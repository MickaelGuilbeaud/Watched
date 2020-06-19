package mg.watched.core.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

inline fun <EventContent> LiveData<Event<EventContent>>.observeEvents(
    owner: LifecycleOwner,
    crossinline eventContentHandler: (EventContent) -> Unit
) {
    observe(owner, Observer { event ->
        event?.getContentIfNotHandled()?.let { eventContent -> eventContentHandler(eventContent) }
    })
}
package mg.template.core.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.util.concurrent.TimeUnit

fun RecyclerView.addNeedToLoadMoreListener(nbRemainingItemsBeforeLoading: Int = 5, callback: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        private var lastEventSentTimestampInMillis: Long = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (layoutManager == null) {
                Timber.w("Layout manager is null, load more listener will not work")
                return
            }
            if (layoutManager !is LinearLayoutManager) {
                Timber.w("Layout manager is not a LinearLayoutManager, load more listener will not work")
                return
            }
            if (layoutManager!!.itemCount == 0) return

            val linearLayoutManager: LinearLayoutManager = layoutManager as LinearLayoutManager

            val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
            val itemThreshold = linearLayoutManager.itemCount - nbRemainingItemsBeforeLoading

            if (lastVisibleItem >= itemThreshold) {
                // Avoid sending too many events
                if (System.currentTimeMillis() > lastEventSentTimestampInMillis + TimeUnit.SECONDS.toMillis(1)) {
                    lastEventSentTimestampInMillis = System.currentTimeMillis()
                    callback()
                }
            }
        }
    })
}
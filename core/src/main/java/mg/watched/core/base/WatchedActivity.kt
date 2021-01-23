package mg.watched.core.base

import android.app.Activity
import androidx.fragment.app.Fragment

interface WatchedActivity {

    fun getFragmentContainerId(): Int

    /**
     * Show or hide a full screen loading that also catch clicks.
     */
    fun showFullScreenLoading(show: Boolean)

    /**
     * Show or hide the bottom navigation view.
     */
    fun showBottomNavigationView(show: Boolean)
}

fun Fragment.requireWatchedActivity(): WatchedActivity {
    val activity: Activity = requireActivity()
    if (activity is WatchedActivity) {
        return activity
    } else {
        throw ClassCastException("${activity::class.java.simpleName} is not an instance of ${WatchedActivity::class.java.simpleName}")
    }
}

package mg.watched.core

import android.app.Activity
import androidx.fragment.app.Fragment

interface FullScreenLoadingHolder {
    fun showLoading(show: Boolean)
}

fun Fragment.requireFullScreenLoadingHolder(): FullScreenLoadingHolder {
    val activity: Activity = requireActivity()
    if (activity is FullScreenLoadingHolder) {
        return activity
    } else {
        throw ClassCastException("${activity::class.java.simpleName} is not an instance of FullScreenLoadingHolder")
    }
}
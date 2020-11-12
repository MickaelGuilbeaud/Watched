package mg.watched.core

import android.app.Activity
import androidx.fragment.app.Fragment

interface FragmentContainerProvider {
    fun getFragmentContainerId(): Int
}

fun Fragment.requireFragmentContainerProvider(): FragmentContainerProvider {
    val activity: Activity = requireActivity()
    if (activity is FragmentContainerProvider) {
        return activity
    } else {
        throw ClassCastException("${activity::class.java.simpleName} is not an instance of FragmentContainerProvider")
    }
}

package mg.template.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import timber.log.Timber

fun BaseFragment.requireBaseActivity() = requireActivity() as BaseActivity

abstract class BaseFragment(private val logsTag: String) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(logsTag)
        Timber.v("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        Timber.tag(logsTag)
        Timber.v("onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(logsTag)
        Timber.v("onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(logsTag)
        Timber.v("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(logsTag)
        Timber.v("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(logsTag)
        Timber.v("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(logsTag)
        Timber.v("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag(logsTag)
        Timber.v("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(logsTag)
        Timber.v("onDestroy")
    }
}
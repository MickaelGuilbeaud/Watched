package mg.template.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber

fun BaseFragment.requireBaseActivity() = requireActivity() as BaseActivity

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(this::class.java.simpleName)
        Timber.v("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        Timber.tag(this::class.java.simpleName)
        Timber.v("onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(this::class.java.simpleName)
        Timber.v("onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(this::class.java.simpleName)
        Timber.v("onStart")
    }

    override fun onResume() {
        super.onResume()

        Timber.tag(this::class.java.simpleName)
        Timber.v("onResume")

        FirebaseAnalytics.getInstance(requireContext())
            .setCurrentScreen(requireActivity(), this::class.java.simpleName, this::class.java.simpleName)
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(this::class.java.simpleName)
        Timber.v("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(this::class.java.simpleName)
        Timber.v("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag(this::class.java.simpleName)
        Timber.v("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(this::class.java.simpleName)
        Timber.v("onDestroy")
    }
}
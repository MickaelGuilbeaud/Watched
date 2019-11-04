package mg.template.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    abstract val logsTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(logsTag)
        Timber.v("onCreate")
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

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(logsTag)
        Timber.v("onDestroy")
    }
}
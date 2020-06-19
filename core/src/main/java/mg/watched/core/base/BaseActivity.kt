package mg.watched.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(this::class.java.simpleName)
        Timber.v("onCreate")
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

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(this::class.java.simpleName)
        Timber.v("onDestroy")
    }
}
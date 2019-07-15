package mg.template.core

import android.app.Application
import timber.log.Timber

class TemplateApplication : Application() {

    companion object {
        lateinit var instance: TemplateApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLogging()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
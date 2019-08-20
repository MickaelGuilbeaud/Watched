package mg.template.core

import android.app.Application
import mg.template.core.utils.CrashlyticsLogsTree
import mg.template.core.utils.CrashlyticsTemplateExceptionsTree
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
        if (BuildConfig.FLAVOR != "dev") {
            Timber.plant(CrashlyticsLogsTree())
            Timber.plant(CrashlyticsTemplateExceptionsTree())
        }
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
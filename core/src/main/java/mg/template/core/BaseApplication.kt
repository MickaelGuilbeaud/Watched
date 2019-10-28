package mg.template.core

import android.app.Application
import mg.template.core.utils.CrashlyticsLogsTree
import mg.template.core.utils.CrashlyticsTemplateExceptionsTree
import timber.log.Timber

abstract class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initDI()
        initLogging()
    }

    abstract fun initDI()

    private fun initLogging() {
        if (buildParams.environment != BuildEnvironment.DEV) {
            Timber.plant(CrashlyticsLogsTree())
            Timber.plant(CrashlyticsTemplateExceptionsTree())
        }
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    abstract val buildParams: BuildParams
}
package mg.template.core.base

import android.app.Application
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import mg.template.core.BuildConfig
import mg.template.core.build.BuildEnvironment
import mg.template.core.build.BuildParams
import mg.template.core.utils.CrashlyticsLogsTree
import mg.template.core.utils.CrashlyticsTemplateExceptionsTree
import timber.log.Timber

abstract class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    abstract val buildParams: BuildParams

    override fun onCreate() {
        super.onCreate()
        instance = this

        initDI()
        initLogging()
        initRx()
    }

    abstract fun initDI()

    private fun initLogging() {
        if (buildParams.environment != BuildEnvironment.DEV) {
            Timber.plant(CrashlyticsLogsTree())
            Timber.plant(CrashlyticsTemplateExceptionsTree())
        }
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initRx() {
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                Timber.w("Caught UndeliverableException")
            } else {
                Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e)
            }
        }
    }
}
package mg.watched.core.base

import android.app.Application
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import mg.watched.core.BuildConfig
import mg.watched.core.build.BuildEnvironment
import mg.watched.core.build.BuildParams
import mg.watched.core.utils.CrashlyticsLogsTree
import mg.watched.core.utils.CrashlyticsWatchedExceptionsTree
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
            Timber.plant(CrashlyticsWatchedExceptionsTree())
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

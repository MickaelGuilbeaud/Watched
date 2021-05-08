package mg.watched.core.base

import android.app.Application
import mg.watched.core.BuildConfig
import mg.watched.core.utils.CrashlyticsLogsTree
import mg.watched.core.utils.CrashlyticsWatchedExceptionsTree
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
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsLogsTree())
            Timber.plant(CrashlyticsWatchedExceptionsTree())
        }
    }
}

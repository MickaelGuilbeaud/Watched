package mg.watched.core.utils

import com.crashlytics.android.Crashlytics
import timber.log.Timber

class CrashlyticsLogsTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Crashlytics.log(message)
    }
}

class CrashlyticsWatchedExceptionsTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (t != null && t is WatchedException) {
            Crashlytics.logException(t)
        }
    }
}
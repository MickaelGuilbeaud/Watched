package mg.watched.core.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashlyticsLogsTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        FirebaseCrashlytics.getInstance().log(message)
    }
}

class CrashlyticsWatchedExceptionsTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (t != null && t is WatchedException) {
            FirebaseCrashlytics.getInstance().recordException(t)
        }
    }
}

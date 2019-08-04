package mg.template.utils

import com.crashlytics.android.Crashlytics
import timber.log.Timber

class CrashlyticsLogsTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Crashlytics.log(message)
    }
}

class CrashlyticsTemplateExceptionsTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (t != null && t is TemplateException) {
            Crashlytics.logException(t)
        }
    }
}
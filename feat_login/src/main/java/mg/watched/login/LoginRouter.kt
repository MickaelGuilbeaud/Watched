package mg.watched.login

import androidx.fragment.app.Fragment
import mg.watched.core.utils.RouterException

interface LoginRouter {
    fun routeToAnimesScreen()
}

interface LoginRouterProvider {
    val loginRouter: LoginRouter
}

internal fun Fragment.requireLoginRouter(): LoginRouter {
    val activity = requireActivity()
    if (activity is LoginRouterProvider) {
        return activity.loginRouter
    } else {
        throw RouterException(LoginRouterProvider::class.java)
    }
}

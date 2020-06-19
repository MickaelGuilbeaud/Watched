package mg.watched.routers

import mg.watched.R
import mg.watched.animes.AnimesFragment
import mg.watched.login.LoginRouter
import mg.watched.main.MainActivity

class LoginRouterImpl(private val activity: MainActivity) : LoginRouter {

    override fun routeToAnimesScreen() {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, AnimesFragment.newInstance())
            .commit()
    }
}
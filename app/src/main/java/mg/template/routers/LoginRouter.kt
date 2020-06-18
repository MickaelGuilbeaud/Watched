package mg.template.routers

import mg.template.R
import mg.template.animes.AnimesFragment
import mg.template.login.LoginRouter
import mg.template.main.MainActivity

class LoginRouterImpl(private val activity: MainActivity) : LoginRouter {

    override fun routeToAnimesScreen() {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, AnimesFragment.newInstance())
            .commit()
    }
}
package mg.watched.routers

import androidx.fragment.app.commit
import mg.watched.animes.animes.AnimesFragment
import mg.watched.core.base.WatchedActivity
import mg.watched.login.LoginRouter
import mg.watched.main.MainActivity

class LoginRouterImpl(private val activity: MainActivity) : LoginRouter {

    override fun routeToAnimesScreen() {
        activity.supportFragmentManager.commit {
            replace((activity as WatchedActivity).getFragmentContainerId(), AnimesFragment.newInstance())
        }
    }
}

package mg.watched.main

import android.os.Bundle
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import mg.watched.R
import mg.watched.animes.AnimesFragment
import mg.watched.animes.AnimesRouter
import mg.watched.animes.AnimesRouterProvider
import mg.watched.core.FullScreenLoadingHolder
import mg.watched.core.base.BaseActivity
import mg.watched.data.authentication.AuthenticationManager
import mg.watched.data.user.UserRepository
import mg.watched.login.LogInFragment
import mg.watched.login.LoginRouter
import mg.watched.login.LoginRouterProvider
import mg.watched.routers.AnimesRouterImpl
import mg.watched.routers.LoginRouterImpl
import org.koin.android.ext.android.get

class MainActivity : BaseActivity(),
    FullScreenLoadingHolder,
    AnimesRouterProvider,
    LoginRouterProvider {

    override val animesRouter: AnimesRouter = AnimesRouterImpl(this)
    override val loginRouter: LoginRouter = LoginRouterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Watched)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            val authenticationManager: AuthenticationManager = get()
            val userRepository: UserRepository = get()
            if (authenticationManager.accessToken != null && userRepository.user != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, AnimesFragment.newInstance())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, LogInFragment.newInstance())
                    .commit()
            }
        }
    }

    override fun showLoading(show: Boolean) {
        vgLoading.isVisible = show
    }
}

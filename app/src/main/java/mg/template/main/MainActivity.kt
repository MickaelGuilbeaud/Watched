package mg.template.main

import android.os.Bundle
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import mg.template.R
import mg.template.animes.AnimesFragment
import mg.template.animes.AnimesRouter
import mg.template.animes.AnimesRouterProvider
import mg.template.core.FullScreenLoadingHolder
import mg.template.core.base.BaseActivity
import mg.template.data.authentication.AuthenticationManager
import mg.template.data.user.UserRepository
import mg.template.login.LogInFragment
import mg.template.login.LoginRouter
import mg.template.login.LoginRouterProvider
import mg.template.routers.AnimesRouterImpl
import mg.template.routers.LoginRouterImpl
import org.koin.android.ext.android.get

class MainActivity : BaseActivity(),
    FullScreenLoadingHolder,
    AnimesRouterProvider,
    LoginRouterProvider {

    override val animesRouter: AnimesRouter = AnimesRouterImpl(this)
    override val loginRouter: LoginRouter = LoginRouterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Template)
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

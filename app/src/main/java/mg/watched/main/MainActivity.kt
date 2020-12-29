package mg.watched.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import mg.watched.R
import mg.watched.animes.animes.AnimesFragment
import mg.watched.animes.animes.AnimesRouter
import mg.watched.animes.animes.AnimesRouterProvider
import mg.watched.core.FragmentContainerProvider
import mg.watched.core.FullScreenLoadingHolder
import mg.watched.core.base.BaseActivity
import mg.watched.data.authentication.AuthenticationManager
import mg.watched.data.user.UserRepository
import mg.watched.databinding.MainActivityBinding
import mg.watched.feat_mangas.list.MangasFragment
import mg.watched.feat_settings.SettingsFragment
import mg.watched.login.LogInFragment
import mg.watched.login.LoginRouter
import mg.watched.login.LoginRouterProvider
import mg.watched.routers.AnimesRouterImpl
import mg.watched.routers.LoginRouterImpl
import org.koin.android.ext.android.get
import timber.log.Timber
import java.security.InvalidParameterException

class MainActivity :
    BaseActivity(),
    FullScreenLoadingHolder,
    FragmentContainerProvider,
    AnimesRouterProvider,
    LoginRouterProvider {

    private lateinit var binding: MainActivityBinding

    override val animesRouter: AnimesRouter = AnimesRouterImpl(this)
    override val loginRouter: LoginRouter = LoginRouterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Watched)
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        if (supportFragmentManager.findFragmentById(getFragmentContainerId()) == null) {
            val authenticationManager: AuthenticationManager = get()
            val userRepository: UserRepository = get()
            if (authenticationManager.accessToken != null && userRepository.user != null) {
                supportFragmentManager.beginTransaction()
                    .replace(getFragmentContainerId(), AnimesFragment.newInstance())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(getFragmentContainerId(), LogInFragment.newInstance())
                    .commit()
            }
        }
    }

    override fun showLoading(show: Boolean) {
        binding.vgLoading.isVisible = show
    }

    override fun getFragmentContainerId(): Int = R.id.fragmentContainer

    // region Bottom navigation

    private fun initBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            when (menuItem.itemId) {
                R.id.navigation_animes -> {
                    Timber.d("Switch to Animes screen")
                    supportFragmentManager.commit { replace(getFragmentContainerId(), AnimesFragment.newInstance()) }
                }
                R.id.navigation_mangas -> {
                    Timber.d("Switch to Mangas screen")
                    supportFragmentManager.commit { replace(getFragmentContainerId(), MangasFragment.newInstance()) }
                }
                R.id.navigation_settings -> {
                    Timber.d("Switch to Settings screen")
                    supportFragmentManager.commit { replace(getFragmentContainerId(), SettingsFragment.newInstance()) }
                }
                else -> throw InvalidParameterException("Bottom navigation menu item not handled")
            }
            true
        }
    }

    // endregion
}

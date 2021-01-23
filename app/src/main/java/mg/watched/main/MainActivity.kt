package mg.watched.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import mg.watched.R
import mg.watched.animes.animes.AnimesFragment
import mg.watched.animes.animes.AnimesRouter
import mg.watched.animes.animes.AnimesRouterProvider
import mg.watched.core.base.BaseActivity
import mg.watched.core.base.WatchedActivity
import mg.watched.core.utils.exhaustive
import mg.watched.core.viewmodel.observeEvents
import mg.watched.databinding.MainActivityBinding
import mg.watched.feat_mangas.list.MangasFragment
import mg.watched.feat_settings.SettingsFragment
import mg.watched.login.LogInFragment
import mg.watched.login.LoginRouter
import mg.watched.login.LoginRouterProvider
import mg.watched.routers.AnimesRouterImpl
import mg.watched.routers.LoginRouterImpl
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.security.InvalidParameterException

class MainActivity :
    BaseActivity(),
    WatchedActivity,
    AnimesRouterProvider,
    LoginRouterProvider {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: MainActivityBinding

    override val animesRouter: AnimesRouter = AnimesRouterImpl(this)
    override val loginRouter: LoginRouter = LoginRouterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Watched)
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        viewModel.navigationEvents().observeEvents(this) { handleNavigationEvent(it) }
        viewModel.actionEvents().observeEvents(this) { handleActionEvent(it) }
    }

    // region ViewStates, NavigationEvents and ActionEvents

    private fun handleNavigationEvent(navigationEvent: MainNavigationEvent) {
        when (navigationEvent) {
            MainNavigationEvent.GoToLogInScreen -> {
                showBottomNavigationView(false)

                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.commit {
                    replace(getFragmentContainerId(), LogInFragment.newInstance())
                }
            }
            MainNavigationEvent.GoToAnimesScreen -> {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.commit {
                    replace(getFragmentContainerId(), AnimesFragment.newInstance())
                }
            }
        }.exhaustive
    }

    private fun handleActionEvent(actionEvent: MainActionEvent) {
        when (actionEvent) {
            MainActionEvent.SessionExpired -> TODO()
        }.exhaustive
    }

    // endregion

    // region Bottom navigation

    private fun initBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            val currentFragment: Fragment? = supportFragmentManager.findFragmentById(getFragmentContainerId())
            currentFragment?.exitTransition = null

            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            when (menuItem.itemId) {
                R.id.navigation_animes -> {
                    Timber.d("Switch to Animes screen")
                    supportFragmentManager.commit {
                        replace(getFragmentContainerId(), AnimesFragment.newInstance())
                    }
                }
                R.id.navigation_mangas -> {
                    Timber.d("Switch to Mangas screen")
                    supportFragmentManager.commit {
                        replace(getFragmentContainerId(), MangasFragment.newInstance())
                    }
                }
                R.id.navigation_settings -> {
                    Timber.d("Switch to Settings screen")
                    supportFragmentManager.commit {
                        replace(getFragmentContainerId(), SettingsFragment.newInstance())
                    }
                }
                else -> throw InvalidParameterException("Bottom navigation menu item not handled")
            }
            true
        }
    }

    // endregion

    // region WatchedActivity implementation

    override fun getFragmentContainerId(): Int = R.id.fragmentContainer

    override fun showFullScreenLoading(show: Boolean) {
        binding.vgLoading.isVisible = show
    }

    override fun showBottomNavigationView(show: Boolean) {
        Timber.d("Show bottom navigation view: $show")
        binding.bottomNavigation.isVisible = show
    }

    // endregion
}

package mg.template

import android.os.Bundle
import mg.template.animes.AnimesFragment
import mg.template.animes.AnimesRouter
import mg.template.animes.AnimesRouterProvider
import mg.template.core.base.BaseActivity
import mg.template.routers.AnimesRouterImpl

class MainActivity : BaseActivity(), AnimesRouterProvider {

    override val logsTag: String = BaseActivity::class.java.simpleName
    override val animesRouter: AnimesRouter = AnimesRouterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Template)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AnimesFragment.newInstance())
                .commit()
        }
    }
}

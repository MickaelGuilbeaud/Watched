package mg.template

import android.os.Bundle
import mg.template.core.base.BaseActivity
import mg.template.pokedex.PokedexFragment
import mg.template.pokedex.PokedexRouter
import mg.template.pokedex.PokedexRouterProvider

class MainActivity : BaseActivity(), PokedexRouterProvider {

    override val logsTag: String = BaseActivity::class.java.simpleName
    override val pokedexRouter: PokedexRouter = PokedexRouterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Template)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, PokedexFragment.newInstance())
            .commit()
    }
}

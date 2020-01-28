package mg.template

import android.os.Bundle
import mg.template.animes.AnimesFragment
import mg.template.core.base.BaseActivity

class MainActivity : BaseActivity() {

    override val logsTag: String = BaseActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Template)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, AnimesFragment.newInstance())
            .commit()
    }
}

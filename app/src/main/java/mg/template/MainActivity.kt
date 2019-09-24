package mg.template

import android.os.Bundle
import mg.template.core.BaseActivity
import mg.template.featurea.FeatureAFragment

class MainActivity : BaseActivity() {

    override val logsTag: String = BaseActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FeatureAFragment.newInstance())
            .commit()
    }
}

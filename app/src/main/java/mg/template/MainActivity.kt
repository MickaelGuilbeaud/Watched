package mg.template

import android.os.Bundle
import mg.template.core.BaseActivity
import mg.template.featurea.ActivityFeatureARouterProvider
import mg.template.featurea.FeatureAFragment
import mg.template.featurea.FeatureARouter

class MainActivity : BaseActivity(), ActivityFeatureARouterProvider {

    override val logsTag: String = BaseActivity::class.java.simpleName
    override val featureARouter: FeatureARouter = FeatureARouterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FeatureAFragment.newInstance())
            .commit()
    }
}

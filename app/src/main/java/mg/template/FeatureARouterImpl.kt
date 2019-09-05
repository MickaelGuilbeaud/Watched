package mg.template

import mg.template.core.BaseActivity
import mg.template.featurea.FeatureARouter
import mg.template.featureb.FeatureBFragment

class FeatureARouterImpl(private val activity: BaseActivity) : FeatureARouter {

    override fun routeToBScreen() {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FeatureBFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}
package mg.template.featurea

import androidx.fragment.app.Fragment
import mg.template.core.utils.RouterException

interface FeatureARouter {
    fun routeToBScreen()
}

interface ActivityFeatureARouterProvider {
    val featureARouter: FeatureARouter
}

internal fun Fragment.getTemplateRouter(): FeatureARouter {
    val activity = requireActivity()
    if (activity is ActivityFeatureARouterProvider) {
        return activity.featureARouter
    } else {
        throw RouterException(ActivityFeatureARouterProvider::class.java)
    }
}
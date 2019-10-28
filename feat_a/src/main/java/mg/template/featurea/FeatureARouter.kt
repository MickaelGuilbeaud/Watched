package mg.template.featurea

import mg.template.core.BaseFragment
import mg.template.core.utils.RouterException

interface FeatureARouter {
    fun routeToBScreen()
}

interface FeatureARouterProvider {
    val featureARouter: FeatureARouter
}

fun BaseFragment.getFeatureARouter(): FeatureARouter {
    val activity = requireActivity()
    if (activity is FeatureARouterProvider) {
        return activity.featureARouter
    } else {
        throw RouterException(FeatureARouterProvider::class.java)
    }
}
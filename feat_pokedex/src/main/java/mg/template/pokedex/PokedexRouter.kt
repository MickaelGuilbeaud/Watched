package mg.template.pokedex

import mg.template.core.base.BaseFragment
import mg.template.core.utils.RouterException

interface PokedexRouter {
    fun routeToBScreen()
}

interface PokedexRouterProvider {
    val pokedexRouter: PokedexRouter
}

internal fun BaseFragment.getPokedexRouter(): PokedexRouter {
    val activity = requireActivity()
    if (activity is PokedexRouterProvider) {
        return activity.pokedexRouter
    } else {
        throw RouterException(PokedexRouterProvider::class.java)
    }
}
package mg.template.animes

import androidx.fragment.app.Fragment
import mg.template.core.utils.RouterException
import mg.template.data.anime.db.models.Anime

interface AnimesRouter {
    fun routeToAnimeDetailScreen(anime: Anime)
}

interface AnimesRouterProvider {
    val animesRouter: AnimesRouter
}

internal fun Fragment.requireAnimesRouter(): AnimesRouter {
    val activity = requireActivity()
    if (activity is AnimesRouterProvider) {
        return activity.animesRouter
    } else {
        throw RouterException(AnimesRouterProvider::class.java)
    }
}
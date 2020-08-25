package mg.watched.animes.animes

import android.view.View
import androidx.fragment.app.Fragment
import mg.watched.core.utils.RouterException
import mg.watched.data.anime.network.models.Anime

interface AnimesRouter {
    fun routeToAnimeDetailScreen(anime: Anime, startAnimationView: View)
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
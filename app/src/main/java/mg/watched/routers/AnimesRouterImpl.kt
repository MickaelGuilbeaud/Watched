package mg.watched.routers

import mg.watched.animes.AnimesRouter
import mg.watched.data.anime.network.models.Anime
import mg.watched.main.MainActivity
import timber.log.Timber

class AnimesRouterImpl(activity: MainActivity) : AnimesRouter {

    override fun routeToAnimeDetailScreen(anime: Anime) {
        Timber.d("On anime selected: ${anime.title}")
        // TODO
    }
}
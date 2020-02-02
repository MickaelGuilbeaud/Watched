package mg.template.routers

import mg.template.MainActivity
import mg.template.animes.AnimesRouter
import mg.template.data.anime.db.models.Anime
import timber.log.Timber

class AnimesRouterImpl(activity: MainActivity) : AnimesRouter {

    override fun routeToAnimeDetailScreen(anime: Anime) {
        Timber.d("On anime selected: ${anime.title}")
        // TODO
    }
}
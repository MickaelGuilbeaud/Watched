package mg.watched.routers

import androidx.fragment.app.commit
import mg.watched.R
import mg.watched.animes.animedetail.AnimeDetailFragment
import mg.watched.animes.animes.AnimesRouter
import mg.watched.data.anime.network.models.Anime
import mg.watched.main.MainActivity

class AnimesRouterImpl(private val activity: MainActivity) : AnimesRouter {

    override fun routeToAnimeDetailScreen(anime: Anime) {
        val fragment = AnimeDetailFragment.newInstance(anime)
        activity.supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragmentContainer, fragment)
        }
    }
}
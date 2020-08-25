package mg.watched.animes.utils

import mg.watched.data.anime.network.models.Anime

object AnimeAnimations {
    /**
     * Create a master detail transition name for the given anime. This transition is used by the master Fragment,
     * detail Fragment and FragmentManager so it's important they use the same transition name.
     */
    fun getAnimeMasterDetailTransitionName(anime: Anime): String = "transition_anime_master_detail_${anime.id}"
}
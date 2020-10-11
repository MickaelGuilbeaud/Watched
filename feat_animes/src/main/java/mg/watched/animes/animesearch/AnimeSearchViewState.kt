package mg.watched.animes.animesearch

import androidx.paging.PagedList
import mg.watched.data.anime.network.models.Anime

sealed class AnimeSearchViewState {
    object NoSearch : AnimeSearchViewState()
    object Loading : AnimeSearchViewState()
    object NoSearchResult : AnimeSearchViewState()
    data class SearchResults(val animes: PagedList<Anime>) : AnimeSearchViewState()
}
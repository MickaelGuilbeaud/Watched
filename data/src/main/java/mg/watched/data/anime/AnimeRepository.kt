package mg.watched.data.anime

import androidx.paging.PagedList
import androidx.paging.toObservable
import io.reactivex.Observable
import mg.watched.core.utils.SchedulerProvider
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime

private sealed class NetworkResourceState {
    object None : NetworkResourceState()
    object Loading : NetworkResourceState()
    object Success : NetworkResourceState()
    data class Error(val error: Throwable) : NetworkResourceState()
}

class AnimeRepository(
    private val animeService: AnimeService,
    private val schedulerProvider: SchedulerProvider
) {

    // region Animes

    private val animeDataSourceFactory: AnimeDataSourceFactory = AnimeDataSourceFactory(animeService)
    private val animePagedListConfig = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()
    val animePagedListStream: Observable<PagedList<Anime>> = animeDataSourceFactory.toObservable(animePagedListConfig)

    // endregion
}
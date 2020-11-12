package mg.watched.data.anime

import androidx.paging.PagedList
import androidx.paging.toObservable
import io.reactivex.Observable
import mg.watched.core.utils.SchedulerProvider
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime

class AnimeRepository(
    private val animeService: AnimeService,
    private val schedulerProvider: SchedulerProvider
) {

    val defaultAnimePagedListConfig = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    // region Animes

    private val animeDataSourceFactory: AnimeDataSourceFactory = AnimeDataSourceFactory(animeService)
    val animePagedListStream: Observable<PagedList<Anime>> =
        animeDataSourceFactory.toObservable(defaultAnimePagedListConfig)

    // endregion

    // region Animes search

    fun createAnimeSearchDaTaSourceFactory(): AnimeSearchDataSourceFactory = AnimeSearchDataSourceFactory(animeService)

    // endregion
}

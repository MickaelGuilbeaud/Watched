package mg.watched.data.anime

import androidx.paging.PagedList
import androidx.paging.toObservable
import io.reactivex.Observable
import io.reactivex.Single
import mg.watched.core.utils.SchedulerProvider
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.AnimeMoshiAdapters
import mg.watched.data.anime.network.models.MyListStatus

class AnimeRepository(
    private val service: AnimeService,
    private val schedulerProvider: SchedulerProvider,
) {

    val defaultAnimePagedListConfig = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    // region Animes

    private val animeDataSourceFactory: AnimeDataSourceFactory = AnimeDataSourceFactory(service)
    val animePagedListStream: Observable<PagedList<Anime>> =
        animeDataSourceFactory.toObservable(defaultAnimePagedListConfig)

    // endregion

    // region Animes search

    fun createSearchDataSourceFactory(): AnimeSearchDataSourceFactory = AnimeSearchDataSourceFactory(service)

    // endregion

    // region Anime detail

    fun updateListStatus(animeId: Long, listStatus: MyListStatus): Single<MyListStatus> =
        service.updateListStatus(
            animeId,
            listStatus.nbEpisodesWatched,
            listStatus.score,
            AnimeMoshiAdapters().watchStatusToJson(listStatus.status),
        )
            .subscribeOn(schedulerProvider.io())

    // endregion
}

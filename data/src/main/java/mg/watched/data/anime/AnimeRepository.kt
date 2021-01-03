package mg.watched.data.anime

import androidx.paging.PagedList
import androidx.paging.toObservable
import io.reactivex.Observable
import mg.watched.core.utils.RResult
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.AnimeMoshiAdapters
import mg.watched.data.anime.network.models.MyListStatus
import mg.watched.data.anime.network.models.WatchStatus

class AnimeRepository(
    private val service: AnimeService,
) {

    val defaultAnimePagedListConfig = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    // region Animes

    private val animeDataSourceFactory: AnimeDataSourceFactory = AnimeDataSourceFactory(service)
    val animePagedListStream: Observable<PagedList<Anime>> =
        animeDataSourceFactory.toObservable(defaultAnimePagedListConfig)

    fun createSearchDataSourceFactory(): AnimeSearchDataSourceFactory = AnimeSearchDataSourceFactory(service)

    // endregion

    // region Anime detail

    suspend fun addToWatchlist(animeId: Long): RResult<MyListStatus> {
        return try {
            val listStatus: MyListStatus =
                service.addToWatchList(animeId, AnimeMoshiAdapters().watchStatusToJson(WatchStatus.PLAN_TO_WATCH))
            RResult.Success(listStatus)
        } catch (e: Exception) {
            RResult.Failure(e)
        }
    }

    suspend fun updateListStatus(animeId: Long, listStatus: MyListStatus): RResult<MyListStatus> {
        return try {
            val updatedListStatus: MyListStatus = service.updateListStatus(
                animeId,
                listStatus.nbEpisodesWatched,
                listStatus.score,
                AnimeMoshiAdapters().watchStatusToJson(listStatus.status),
            )
            RResult.Success(updatedListStatus)
        } catch (e: Exception) {
            RResult.Failure(e)
        }
    }

    // endregion
}


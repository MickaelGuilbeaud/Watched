package mg.watched.data.anime

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime
import timber.log.Timber

class AnimeDataSource(
    private val animeService: AnimeService,
    private val viewModelScope: CoroutineScope,
) : PositionalDataSource<Anime>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Anime>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val animes: List<Anime> = animeService.getUserAnimesPaginated(pageSize = params.pageSize)
                    .data
                    .map { it.node }
                callback.onResult(animes, 0)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e, "Load animes failed")
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Anime>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val animes: List<Anime> = animeService.getUserAnimesPaginated(
                    pageSize = params.loadSize,
                    offset = params.startPosition,
                )
                    .data
                    .map { it.node }
                callback.onResult(animes)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e, "Load animes failed")
            }
        }
    }
}

class AnimeDataSourceFactory(
    private val animeService: AnimeService,
    private val viewModelScope: CoroutineScope,
) : DataSource.Factory<Int, Anime>() {

    private var dataSource: AnimeDataSource? = null

    override fun create(): DataSource<Int, Anime> {
        dataSource = AnimeDataSource(animeService, viewModelScope)
        return dataSource!!
    }

    fun invalidate() {
        dataSource?.invalidate()
    }
}

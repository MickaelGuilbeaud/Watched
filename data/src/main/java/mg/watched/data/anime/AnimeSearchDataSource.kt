package mg.watched.data.anime

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime
import timber.log.Timber

class AnimeSearchDataSource(
    private val animeService: AnimeService,
    private val searchTerm: String,
    private val viewModelScope: CoroutineScope,
) : PositionalDataSource<Anime>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Anime>) {
        viewModelScope.launch {
            try {
                val animes: List<Anime> = animeService.searchAnimes(
                    searchTerm = searchTerm,
                    pageSize = params.pageSize,
                )
                    .data
                    .map { it.node }
                callback.onResult(animes, 0)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e, "Search animes failed")
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Anime>) {
        viewModelScope.launch {
            try {
                val animes: List<Anime> = animeService.searchAnimes(
                    searchTerm = searchTerm,
                    pageSize = params.loadSize,
                    offset = params.startPosition,
                )
                    .data
                    .map { it.node }
                callback.onResult(animes)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e, "Search animes failed")
            }
        }
    }
}

class AnimeSearchDataSourceFactory(
    private val animeService: AnimeService,
    private val viewModelScope: CoroutineScope,
) : DataSource.Factory<Int, Anime>() {

    private var dataSource: AnimeSearchDataSource? = null
    private var searchTerm: String = "aaa"

    override fun create(): DataSource<Int, Anime> {
        dataSource = AnimeSearchDataSource(animeService, searchTerm, viewModelScope)
        return dataSource!!
    }

    fun search(searchTerm: String) {
        this.searchTerm = searchTerm
        invalidate()
    }

    fun invalidate() {
        dataSource?.invalidate()
    }
}

package mg.watched.data.anime

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.AnimesWrapper

class AnimeSearchDataSource(
    private val animeService: AnimeService,
    private val searchTerm: String
) : PositionalDataSource<Anime>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Anime>) {
        // TODO: Handle errors
        val paginatedAnimes: AnimesWrapper = animeService.searchAnimes(
            searchTerm = searchTerm,
            pageSize = params.pageSize
        )
            .blockingGet()
        val animes: List<Anime> = paginatedAnimes.data.map { it.node }
        callback.onResult(animes, 0)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Anime>) {
        // TODO: Handle errors
        val paginatedAnimes: AnimesWrapper = animeService.searchAnimes(
            searchTerm = searchTerm,
            pageSize = params.loadSize,
            offset = params.startPosition
        )
            .blockingGet()
        val animes: List<Anime> = paginatedAnimes.data.map { it.node }
        callback.onResult(animes)
    }
}

class AnimeSearchDataSourceFactory(private val animeService: AnimeService) : DataSource.Factory<Int, Anime>() {

    private var dataSource: AnimeSearchDataSource? = null
    private var searchTerm: String = "aaa"

    override fun create(): DataSource<Int, Anime> {
        dataSource = AnimeSearchDataSource(animeService, searchTerm)
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
package mg.watched.data.anime

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.AnimesWrapper

class AnimeDataSource(private val animeService: AnimeService) : PositionalDataSource<Anime>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Anime>) {
        // TODO: Handle errors
        val paginatedAnimes: AnimesWrapper = animeService.getUserAnimesPaginated(pageSize = params.pageSize)
            .blockingGet()
        val animes: List<Anime> = paginatedAnimes.data.map { it.node }
        callback.onResult(animes, 0)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Anime>) {
        // TODO: Handle errors
        val paginatedAnimes: AnimesWrapper = animeService.getUserAnimesPaginated(
            pageSize = params.loadSize,
            offset = params.startPosition
        )
            .blockingGet()
        val animes: List<Anime> = paginatedAnimes.data.map { it.node }
        callback.onResult(animes)
    }
}

class AnimeDataSourceFactory(private val animeService: AnimeService) : DataSource.Factory<Int, Anime>() {
    private var dataSource: AnimeDataSource? = null

    override fun create(): DataSource<Int, Anime> {
        dataSource = AnimeDataSource(animeService)
        return dataSource!!
    }

    fun invalidate() {
        dataSource?.invalidate()
    }
}

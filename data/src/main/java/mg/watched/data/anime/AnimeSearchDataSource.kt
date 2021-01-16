package mg.watched.data.anime

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.AnimesWrapper
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class AnimeSearchDataSource(
    private val animeService: AnimeService,
    private val searchTerm: String,
) : PositionalDataSource<Anime>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Anime>) {
        Timber.d("Load initial anime search")
        val call: Call<AnimesWrapper> = animeService.searchAnimes(
            searchTerm = searchTerm,
            pageSize = params.pageSize,
            offset = 0,
        )
        val response: Response<AnimesWrapper> = call.execute()

        if (response.isSuccessful) {
            val animes: List<Anime> = response.body()!!.data.map { it.node }
            Timber.d("Load initial anime search successful. Received ${animes.size} animes")
            callback.onResult(animes, 0)
        } else {
            val error = Exception(response.errorBody()!!.string())
            Timber.e(error, "Load initial anime search failed")
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Anime>) {
        Timber.d("Load range anime search")
        val call: Call<AnimesWrapper> = animeService.searchAnimes(
            searchTerm = searchTerm,
            pageSize = params.loadSize,
            offset = params.startPosition,
        )
        val response: Response<AnimesWrapper> = call.execute()

        if (response.isSuccessful) {
            val animes: List<Anime> = response.body()!!.data.map { it.node }
            Timber.d("Load range anime search successful. Received ${animes.size} animes")
            callback.onResult(animes)
        } else {
            val error = Exception(response.errorBody()!!.string())
            Timber.e(error, "Load range anime search failed")
        }
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

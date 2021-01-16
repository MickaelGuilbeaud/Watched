package mg.watched.data.anime

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import mg.watched.data.anime.network.AnimeService
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.AnimesWrapper
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class AnimeDataSource(private val animeService: AnimeService) : PositionalDataSource<Anime>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Anime>) {
        Timber.d("Load initial animes")
        val call: Call<AnimesWrapper> = animeService.getUserAnimesPaginated(
            pageSize = params.pageSize,
            offset = 0,
        )
        val response: Response<AnimesWrapper> = call.execute()

        if (response.isSuccessful) {
            val animes: List<Anime> = response.body()!!.data.map { it.node }
            Timber.d("Load initial animes successful. Received ${animes.size} animes")
            callback.onResult(animes, 0)
        } else {
            val error = Exception(response.errorBody()!!.string())
            Timber.e(error, "Load initial animes failed")
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Anime>) {
        Timber.d("Load range animes")
        val call: Call<AnimesWrapper> = animeService.getUserAnimesPaginated(
            pageSize = params.loadSize,
            offset = params.startPosition,
        )
        val response: Response<AnimesWrapper> = call.execute()

        if (response.isSuccessful) {
            val animes: List<Anime> = response.body()!!.data.map { it.node }
            Timber.d("Load range animes successful. Received ${animes.size} animes")
            callback.onResult(animes)
        } else {
            val error = Exception(response.errorBody()!!.string())
            Timber.e(error, "Load range animes failed")
        }
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

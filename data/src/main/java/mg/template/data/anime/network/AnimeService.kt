package mg.template.data.anime.network

import io.reactivex.Single
import mg.template.data.anime.network.models.AnimesEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {

    @GET("season/{year}/{season}")
    fun getSeasonAnimes(
        @Path("year") year: String,
        @Path("season") season: String
    ): Single<AnimesEntity>
}
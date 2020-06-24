package mg.watched.data.anime.network

import io.reactivex.Single
import mg.watched.data.anime.network.models.AnimesWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {

    @GET("users/@me/animelist")
    fun getUserAnimesPaginated(
        @Query("fields") fields: String = "media_type,start_season,num_episodes,status,my_list_status",
        @Query("limit") pageSize: Int = 10,
        @Query("offset") offset: Int = 0
    ): Single<AnimesWrapper>
}
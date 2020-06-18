package mg.template.data.anime.network

import io.reactivex.Single
import mg.template.data.anime.network.models.AnimesWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {

    @GET("users/@me/animelist")
    fun getUserAnimes(
        @Query("fields") fields: String = "media_type,start_season,num_episodes,status,my_list_status"
    ): Single<AnimesWrapper>
}
package mg.template.data.anime.network

import io.reactivex.Single
import mg.template.data.anime.network.models.AnimesWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {

    @GET("users/{user_id}/animelist")
    fun getUserAnimes(
        @Path("user_id") userId: String
    ): Single<AnimesWrapper>
}
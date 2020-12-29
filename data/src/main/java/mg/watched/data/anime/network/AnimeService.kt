package mg.watched.data.anime.network

import io.reactivex.Single
import mg.watched.data.anime.network.models.AnimesWrapper
import mg.watched.data.anime.network.models.MyListStatus
import retrofit2.http.*

private const val additionalFields: String = "alternative_titles,synopsis,media_type,start_season,num_episodes," +
    "status,my_list_status"

interface AnimeService {

    @GET("users/@me/animelist")
    fun getUserAnimesPaginated(
        @Query("fields") fields: String = additionalFields,
        @Query("limit") pageSize: Int = 10,
        @Query("offset") offset: Int = 0,
    ): Single<AnimesWrapper>

    @GET("anime")
    fun searchAnimes(
        @Query("q") searchTerm: String,
        @Query("fields") fields: String = additionalFields,
        @Query("limit") pageSize: Int = 10,
        @Query("offset") offset: Int = 0,
    ): Single<AnimesWrapper>

    @FormUrlEncoded
    @PATCH("anime/{anime_id}/my_list_status")
    fun addToWatchList(
        @Path("anime_id") animeId: Long,
        @Field("status") watchStatus: String,
    ): Single<MyListStatus>

    @FormUrlEncoded
    @PATCH("anime/{anime_id}/my_list_status")
    fun updateListStatus(
        @Path("anime_id") animeId: Long,
        @Field("num_watched_episodes") nbWatchedEpisodes: Int,
        @Field("score") score: Double,
        @Field("status") watchStatus: String,
    ): Single<MyListStatus>
}

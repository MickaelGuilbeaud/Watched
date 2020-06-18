package mg.template.data.anime.network.models

import com.squareup.moshi.Json

data class AnimesWrapper(val data: List<AnimeWrapper> = emptyList())

data class AnimeWrapper(val node: Anime)

data class Anime(
    val id: Long,
    val title: String,
    @Json(name = "main_picture") val mainPicture: AnimePicture,
    @Json(name = "media_type") val mediaType: MediaType,
    @Json(name = "start_season") val startSeason: Season,
    @Json(name = "num_episodes") val nbEpisodes: Int,
    @Json(name = "status") val airingStatus: AiringStatus,
    @Json(name = "my_list_status") val myListStatus: MyListStatus
)

data class AnimePicture(
    @Json(name = "large") val largeUrl: String,
    @Json(name = "medium") val mediumUrl: String
)

enum class MediaType {
    MOVIE,
    ONA,
    TV
}

data class Season(
    val season: String,
    val year: Int
)

enum class AiringStatus {
    CURRENTLY_AIRING,
    FINISHED_AIRING
}

data class MyListStatus(
    @Json(name = "num_episodes_watched") val nbEpisodesWatched: Int,
    val score: Double,
    val status: WatchStatus
)

enum class WatchStatus {
    COMPLETED,
    DROPPED,
    ON_HOLD,
    PLAN_TO_WATCH,
    WATCHING
}
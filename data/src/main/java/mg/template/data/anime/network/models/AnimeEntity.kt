package mg.template.data.anime.network.models

import com.squareup.moshi.Json

data class AnimesWrapper(val data: List<Anime> = emptyList())

data class Anime(
    val id: Long,
    val title: String,
    @Json(name = "main_picture") val mainPicture: AnimePicture,
    @Json(name = "my_list_status") val myListStatus: MyListStatus
)

data class AnimePicture(
    @Json(name = "large") val largeUrl: String,
    @Json(name = "medium") val mediumUrl: String
)

data class MyListStatus(
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
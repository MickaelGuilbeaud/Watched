package mg.watched.data.anime.network.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class AnimesWrapper(val data: List<AnimeWrapper> = emptyList())

data class AnimeWrapper(val node: Anime)

@Parcelize
data class Anime(
    val id: Long,
    val title: String,
    @Json(name = "alternative_titles") val alternativeTitles: AlternativeTitles,
    val synopsis: String,
    @Json(name = "main_picture") val mainPicture: AnimePicture,
    @Json(name = "media_type") val mediaType: MediaType,
    @Json(name = "start_season") val startSeason: Season?,
    @Json(name = "num_episodes") val nbEpisodes: Int,
    @Json(name = "status") val airingStatus: AiringStatus,
    @Json(name = "my_list_status") val myListStatus: MyListStatus?,
) : Parcelable

@Parcelize
data class AlternativeTitles(
    @Json(name = "en") val englishTitle: String,
    @Json(name = "ja") val japaneseTitle: String,
    val synonyms: List<String> = emptyList(),
) : Parcelable

@Parcelize
data class AnimePicture(
    @Json(name = "large") val largeUrl: String,
    @Json(name = "medium") val mediumUrl: String,
) : Parcelable

enum class MediaType {
    MOVIE,
    MUSIC,
    ONA,
    OVA,
    SPECIAL,
    TV,
}

@Parcelize
data class Season(
    val season: String,
    val year: Int,
) : Parcelable

enum class AiringStatus {
    CURRENTLY_AIRING,
    FINISHED_AIRING,
    NOT_YET_AIRED,
}

@Parcelize
data class MyListStatus(
    @Json(name = "num_episodes_watched") val nbEpisodesWatched: Int,
    val score: Double,
    val status: WatchStatus,
) : Parcelable

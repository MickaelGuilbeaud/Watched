package mg.watched.data.anime.network.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class AnimeMoshiAdapters {

    @ToJson
    fun mediaTypeToJson(mediaType: MediaType): String = mediaType.toString().toLowerCase(Locale.getDefault())

    @FromJson
    fun mediaTypeFromJson(mediaType: String): MediaType = MediaType.valueOf(mediaType.toUpperCase(Locale.getDefault()))

    @ToJson
    fun airingStatusToJson(airingStatus: AiringStatus): String =
        airingStatus.toString().toLowerCase(Locale.getDefault())

    @FromJson
    fun airingStatusFromJson(airingStatus: String): AiringStatus =
        AiringStatus.valueOf(airingStatus.toUpperCase(Locale.getDefault()))

    @ToJson
    fun watchStatusToJson(watchStatus: WatchStatus): String = watchStatus.toString().toLowerCase(Locale.getDefault())

    @FromJson
    fun watchStatusFromJson(watchStatus: String): WatchStatus =
        WatchStatus.valueOf(watchStatus.toUpperCase(Locale.getDefault()))
}

package mg.watched.data.anime.network.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class AnimeMoshiAdapters {

    @ToJson
    fun mediaTypeToJson(mediaType: MediaType): String = mediaType.toString().toLowerCase()

    @FromJson
    fun mediaTypeFromJson(mediaType: String): MediaType = MediaType.valueOf(mediaType.toUpperCase())

    @ToJson
    fun airingStatusToJson(airingStatus: AiringStatus): String = airingStatus.toString().toLowerCase()

    @FromJson
    fun airingStatusFromJson(airingStatus: String): AiringStatus = AiringStatus.valueOf(airingStatus.toUpperCase())

    @ToJson
    fun watchStatusToJson(watchStatus: WatchStatus): String = watchStatus.toString().toLowerCase()

    @FromJson
    fun watchStatusFromJson(watchStatus: String): WatchStatus = WatchStatus.valueOf(watchStatus.toUpperCase())
}
package mg.watched.data.anime.network.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class AnimeMoshiAdapters {

    @ToJson
    fun mediaTypeToJson(mediaType: MediaType): String = mediaType.toString().lowercase()

    @FromJson
    fun mediaTypeFromJson(mediaType: String): MediaType = MediaType.valueOf(mediaType.uppercase())

    @ToJson
    fun airingStatusToJson(airingStatus: AiringStatus): String = airingStatus.toString().lowercase()

    @FromJson
    fun airingStatusFromJson(airingStatus: String): AiringStatus = AiringStatus.valueOf(airingStatus.uppercase())

    @ToJson
    fun watchStatusToJson(watchStatus: WatchStatus): String = watchStatus.toString().lowercase()

    @FromJson
    fun watchStatusFromJson(watchStatus: String): WatchStatus = WatchStatus.valueOf(watchStatus.uppercase())
}

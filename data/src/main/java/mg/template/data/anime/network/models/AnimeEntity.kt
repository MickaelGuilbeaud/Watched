package mg.template.data.anime.network.models

import com.squareup.moshi.Json

data class AnimesEntity(val anime: List<AnimeEntity> = emptyList())

data class AnimeEntity(
    @Json(name = "mal_id") val malId: Long,
    val title: String,
    @Json(name = "image_url") val imageUrl: String,
    val synopsis: String,
    val episodes: Int?,
    val genres: List<GenreEntity> = emptyList(),
    val producers: List<ProducerEntity> = emptyList(),
    val score: Double?
)

data class GenreEntity(val name: String)

data class ProducerEntity(val name: String)
package mg.template.data.pokemon.network

import com.squareup.moshi.Json

data class Pokemon(
    val id: Long,
    val order: Long,
    val name: String,
    val sprites: Sprites
)

data class Sprites(
    @Json(name = "front_default") val frontDefault: String,
    @Json(name = "back_default") val backDefault: String
)
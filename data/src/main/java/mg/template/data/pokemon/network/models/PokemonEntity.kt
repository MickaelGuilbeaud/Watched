package mg.template.data.pokemon.network.models

import com.squareup.moshi.Json

data class PokemonEntity(
    val id: Long,
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonType>
)

data class Sprites(
    @Json(name = "front_default") val frontDefault: String,
    @Json(name = "back_default") val backDefault: String
)

data class PokemonTypeEntity(
    val slot: Int,
    @Json(name = "type") val typeDetails: PokemonTypeDetails
)

data class PokemonTypeDetails(val name: String)
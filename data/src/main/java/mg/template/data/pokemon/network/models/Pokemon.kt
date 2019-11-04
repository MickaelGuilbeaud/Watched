package mg.template.data.pokemon.network.models

import com.squareup.moshi.Json

data class Pokemon(
    val id: Long,
    val order: Long,
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonType>
)

data class Sprites(
    @Json(name = "front_default") val frontDefault: String,
    @Json(name = "back_default") val backDefault: String
)

data class PokemonTypeJson(
    val slot: Int,
    @Json(name = "type") val typeDetails: PokemonTypeDetails
)

data class PokemonTypeDetails(val name: String)

enum class PokemonType {
    BUG,
    DARK,
    DRAGON,
    ELECTRIC,
    FAIRY,
    FIGHTING,
    FIRE,
    FLYING,
    GHOST,
    GRASS,
    GROUND,
    ICE,
    NORMAL,
    POISON,
    PSYCHIC,
    ROCK,
    STEEL,
    WATER
}
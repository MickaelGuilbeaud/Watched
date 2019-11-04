package mg.template.data.pokemon.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import mg.template.data.pokemon.network.models.PokemonType
import mg.template.data.pokemon.network.models.PokemonTypeJson

class PokemonTypeAdapter {

    @ToJson
    fun toJson(pokemonTypes: List<PokemonType>): List<PokemonTypeJson> {
        TODO()
    }

    @FromJson
    fun fromJson(pokemonTypesJson: List<PokemonTypeJson>): List<PokemonType> {
        return pokemonTypesJson.map { pokemonTypeJson ->
            Pair(pokemonTypeJson.slot, PokemonType.valueOf(pokemonTypeJson.typeDetails.name.toUpperCase()))
        }
            .sortedBy { it.first }
            .map { it.second }
    }
}
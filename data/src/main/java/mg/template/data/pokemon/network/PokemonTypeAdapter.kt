package mg.template.data.pokemon.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import mg.template.data.pokemon.network.models.PokemonType
import mg.template.data.pokemon.network.models.PokemonTypeEntity

internal class PokemonTypeAdapter {

    @ToJson
    fun toJson(pokemonTypes: List<PokemonType>): List<PokemonTypeEntity> {
        throw NotImplementedError("pokemonTypes to json is not implemented")
    }

    @FromJson
    fun fromJson(pokemonTypesEntity: List<PokemonTypeEntity>): List<PokemonType> {
        return pokemonTypesEntity.map { pokemonTypeJson ->
            Pair(pokemonTypeJson.slot, PokemonType.valueOf(pokemonTypeJson.typeDetails.name.toUpperCase()))
        }
            .sortedBy { it.first }
            .map { it.second }
    }
}
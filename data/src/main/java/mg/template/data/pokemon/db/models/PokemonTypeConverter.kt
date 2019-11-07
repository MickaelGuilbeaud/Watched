package mg.template.data.pokemon.db.models

import androidx.room.TypeConverter
import mg.template.data.pokemon.network.models.PokemonType

internal class RoomTypeConverters {

    @TypeConverter
    fun fromPokemonType(pokemonType: PokemonType?): String? = pokemonType?.toString()

    @TypeConverter
    fun toPokemonType(pokemonTypeStr: String?): PokemonType? =
        if (pokemonTypeStr == null) null else PokemonType.valueOf(pokemonTypeStr)
}
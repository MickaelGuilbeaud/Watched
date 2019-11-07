package mg.template.data.pokemon.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mg.template.data.pokemon.network.models.PokemonType

@Entity
data class Pokemon(
    @PrimaryKey val id: Long,
    val name: String,
    @ColumnInfo(name = "front_sprite_url") val frontSpriteUrl: String,
    @ColumnInfo(name = "back_sprite_url") val backSpriteUrl: String,
    val type1: PokemonType,
    val type2: PokemonType?
)
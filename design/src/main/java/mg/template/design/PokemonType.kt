package mg.template.design

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import mg.template.data.pokemon.network.models.PokemonType

@StringRes
fun PokemonType.getName(): Int = when (this) {
    PokemonType.BUG -> R.string.type_bug
    PokemonType.DARK -> R.string.type_dark
    PokemonType.DRAGON -> R.string.type_dragon
    PokemonType.ELECTRIC -> R.string.type_electric
    PokemonType.FAIRY -> R.string.type_fairy
    PokemonType.FIGHTING -> R.string.type_fighting
    PokemonType.FIRE -> R.string.type_fire
    PokemonType.FLYING -> R.string.type_flying
    PokemonType.GHOST -> R.string.type_ghost
    PokemonType.GRASS -> R.string.type_grass
    PokemonType.GROUND -> R.string.type_ground
    PokemonType.ICE -> R.string.type_ice
    PokemonType.NORMAL -> R.string.type_normal
    PokemonType.POISON -> R.string.type_poison
    PokemonType.PSYCHIC -> R.string.type_psychic
    PokemonType.ROCK -> R.string.type_rock
    PokemonType.STEEL -> R.string.type_steel
    PokemonType.WATER -> R.string.type_water
}

@ColorRes
fun PokemonType.getColor(): Int = when (this) {
    PokemonType.BUG -> R.color.type_bug
    PokemonType.DARK -> R.color.type_dark
    PokemonType.DRAGON -> R.color.type_dragon
    PokemonType.ELECTRIC -> R.color.type_electric
    PokemonType.FAIRY -> R.color.type_fairy
    PokemonType.FIGHTING -> R.color.type_fighting
    PokemonType.FIRE -> R.color.type_fire
    PokemonType.FLYING -> R.color.type_flying
    PokemonType.GHOST -> R.color.type_ghost
    PokemonType.GRASS -> R.color.type_grass
    PokemonType.GROUND -> R.color.type_ground
    PokemonType.ICE -> R.color.type_ice
    PokemonType.NORMAL -> R.color.type_normal
    PokemonType.POISON -> R.color.type_poison
    PokemonType.PSYCHIC -> R.color.type_psychic
    PokemonType.ROCK -> R.color.type_rock
    PokemonType.STEEL -> R.color.type_steel
    PokemonType.WATER -> R.color.type_water
}

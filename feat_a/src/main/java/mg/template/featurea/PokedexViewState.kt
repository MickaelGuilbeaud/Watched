package mg.template.featurea

import mg.template.data.pokemon.network.Pokemon

sealed class PokedexViewState {

    data class Pokemons(val pokemons: List<Pokemon>) : PokedexViewState()
}
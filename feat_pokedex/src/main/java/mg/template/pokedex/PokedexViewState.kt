package mg.template.pokedex

import mg.template.data.pokemon.network.models.Pokemon

sealed class PokedexViewState {
    object Loading : PokedexViewState()
    data class Error(val error: Throwable) : PokedexViewState()
    data class Pokemons(val pokemons: List<Pokemon>) : PokedexViewState()
}
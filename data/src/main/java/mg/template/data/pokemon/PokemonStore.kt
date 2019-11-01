package mg.template.data.pokemon

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import mg.template.data.pokemon.network.Pokemon
import mg.template.data.pokemon.network.PokemonService
import timber.log.Timber

class PokemonStore(private val pokemonService: PokemonService) {

    fun getPokemonsOnce(): Single<List<Pokemon>> {
        Timber.d("Get Pokemons")

        return Observable.rangeLong(1, 20)
            .concatMapEager { pokemonId ->
                pokemonService.getPokemonDetails(pokemonId)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
            }
            .toList()
    }
}
package mg.template.data.pokemon

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import mg.template.core.Lce
import mg.template.data.pokemon.network.PokemonService
import mg.template.data.pokemon.network.models.Pokemon
import timber.log.Timber

class PokemonStore(private val pokemonService: PokemonService) {

    private val compositeDisposable = CompositeDisposable()

    // region Pokemons

    private var pokemons: List<Pokemon>? = null
    private var isLoadingPokemons: Boolean = false

    private val pokemonsSubject = BehaviorSubject.create<Lce<List<Pokemon>>>()

    fun pokemonsStream(): Observable<Lce<List<Pokemon>>> = pokemonsSubject.hide()
        .doOnSubscribe { if (pokemons == null && !isLoadingPokemons) getPokemons() }

    private fun getPokemons() {
        Observable.rangeLong(1, 20)
            .concatMapEager { pokemonId ->
                pokemonService.getPokemonDetails(pokemonId)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
            }
            .toList()
            .doOnSubscribe {
                Timber.d("Get Pokémons")
                pokemonsSubject.onNext(Lce.Loading(pokemons))
            }
            .subscribeBy(onSuccess = { pokemons ->
                Timber.d("Successfully retrieved ${pokemons.size} Pokémons")
                this.pokemons = pokemons
                pokemonsSubject.onNext(Lce.Content(this.pokemons!!))
            }, onError = { error ->
                Timber.e(error, "Get Pokémons failed")
                pokemonsSubject.onNext(Lce.Error(error))
            })
            .addTo(compositeDisposable)
    }

    // endregion
}
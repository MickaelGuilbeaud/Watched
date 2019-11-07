package mg.template.data.pokemon

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import mg.template.core.Lce
import mg.template.data.pokemon.db.PokemonDao
import mg.template.data.pokemon.db.models.Pokemon
import mg.template.data.pokemon.network.PokemonService
import timber.log.Timber

class PokemonStore(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao
) {

    private val compositeDisposable = CompositeDisposable()

    // region Pokemons

    private var pokemons: List<Pokemon>? = null
    private var isLoadingPokemons: Boolean = false

    private val pokemonsSubject = BehaviorSubject.create<Lce<List<Pokemon>>>()

    fun pokemonsStream(): Observable<Lce<List<Pokemon>>> = pokemonsSubject.hide()
        .doOnSubscribe { checkPokemonsData() }

    private fun checkPokemonsData() {
        // Avoid updating Pokemons data multiple times in parallel
        if (isLoadingPokemons) return

        if (pokemons == null) {
            isLoadingPokemons = true
            getPokemonsFromDB()
        } else {
            if (isPokemonsDataOutdated()) {
                isLoadingPokemons = true
                getPokemonsFromNetwork()
            } else {
                // Nothing to do ?
            }
        }
    }

    private fun isPokemonsDataOutdated(): Boolean = false

    private fun getPokemonsFromDB() {
        pokemonDao.getPokemonsInRange(1, 20)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                Timber.d("Get Pokémons from DB")
                pokemonsSubject.onNext(Lce.Loading(pokemons))
            }
            .subscribeBy(onSuccess = { dbPokemons ->
                Timber.d("Successfully retrieved ${dbPokemons.size} Pokémons from DB")
                if (dbPokemons.isEmpty()) {
                    getPokemonsFromNetwork()
                } else {
                    pokemons = dbPokemons

                    if (isPokemonsDataOutdated()) {
                        pokemonsSubject.onNext(Lce.Loading(pokemons))
                        getPokemonsFromNetwork()
                    } else {
                        isLoadingPokemons = false
                        pokemonsSubject.onNext(Lce.Content(pokemons!!))
                    }
                }
            }, onError = {
                Timber.e(it, "Failed to retrieve Pokémons from DB")
                getPokemonsFromNetwork()
            })
            .addTo(compositeDisposable)
    }

    private fun getPokemonsFromNetwork() {
        Observable.rangeLong(1, 20)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                Timber.d("Get Pokémons from Network")
                pokemonsSubject.onNext(Lce.Loading(pokemons))
            }
            .concatMapEager { pokemonId ->
                pokemonService.getPokemonDetails(pokemonId)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
            }
            .toList()
            .map { pokemons ->
                pokemons.map { pokemon ->
                    Pokemon(
                        pokemon.id,
                        pokemon.name.capitalize(),
                        pokemon.sprites.frontDefault,
                        pokemon.sprites.backDefault,
                        pokemon.types[0],
                        pokemon.types.getOrNull(1)
                    )
                }
            }
            .doOnSuccess { pokemons ->
                Timber.d("Save ${pokemons.size} Pokémons in DB")
                pokemonDao.deletePokemonsInRange(0, pokemons.size)
                pokemonDao.insertAll(pokemons)
            }
            .subscribeBy(onSuccess = { pokemons ->
                Timber.d("Successfully retrieved ${pokemons.size} Pokémons from network")
                this.pokemons = pokemons
                pokemonsSubject.onNext(Lce.Content(this.pokemons!!))
            }, onError = { error ->
                Timber.e(error, "Get Pokémons from network failed")
                pokemonsSubject.onNext(Lce.Error(error))
            })
            .addTo(compositeDisposable)
    }

    // endregion
}
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

    companion object {
        private const val pageSize: Long = 20L
    }

    private val compositeDisposable = CompositeDisposable()

    // region Pokemons

    private var pokemons: List<Pokemon> = emptyList()
    private var isLoadingPokemons: Boolean = false

    private var pokemonsCurrentPageNumber: Int = 0

    private val pokemonsSubject = BehaviorSubject.create<Lce<List<Pokemon>>>()

    fun pokemonsStream(): Observable<Lce<List<Pokemon>>> = pokemonsSubject.hide()
        .doOnSubscribe { checkPokemonsData() }

    private fun checkPokemonsData() {
        // Avoid updating Pokemons data multiple times in parallel
        if (isLoadingPokemons) return

        if (pokemons.isEmpty()) {
            isLoadingPokemons = true
            getPokemonsFromDB(0)
        } else if (isPokemonsDataOutdated()) {
            isLoadingPokemons = true
            getPokemonsFromNetwork(0)
        }
    }

    private fun isPokemonsDataOutdated(): Boolean = false

    private fun getPokemonsFromDB(pageNumber: Int) {
        val startIndex = pageNumber * pageSize + 1
        val endIndex = startIndex + pageSize - 1
        pokemonDao.getPokemonsInRange(startIndex, endIndex)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                Timber.d("Get Pokémons page $pageNumber ($startIndex to $endIndex) from DB")
                isLoadingPokemons = true
                pokemonsSubject.onNext(Lce.Loading(pokemons))
            }
            .subscribeBy(onSuccess = { dbPokemons ->
                Timber.d("Successfully retrieved ${dbPokemons.size} Pokémons from DB")
                if (dbPokemons.isEmpty()) {
                    getPokemonsFromNetwork(pageNumber)
                } else {
                    pokemons = if (pageNumber == 0) dbPokemons else pokemons + dbPokemons

                    if (isPokemonsDataOutdated()) {
                        pokemonsSubject.onNext(Lce.Loading(pokemons))
                        getPokemonsFromNetwork(pageNumber)
                    } else {
                        isLoadingPokemons = false
                        pokemonsSubject.onNext(Lce.Content(pokemons))
                    }
                }
            }, onError = {
                Timber.e(it, "Failed to retrieve Pokémons from DB")
                getPokemonsFromNetwork(0)
            })
            .addTo(compositeDisposable)
    }

    private fun getPokemonsFromNetwork(pageNumber: Int) {
        val startIndex = pageNumber * pageSize + 1
        Observable.rangeLong(startIndex, pageSize)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                Timber.d("Get Pokémons page $pageNumber (from $startIndex, $pageSize Pokémons} from Network")
                isLoadingPokemons = true
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
                        pokemon.types[0],
                        pokemon.types.getOrNull(1)
                    )
                }
            }
            .doOnSuccess { pokemons ->
                Timber.d("Delete Pokémons from id ${pokemons.first().id} to ${pokemons.last().id}")
                pokemonDao.deletePokemonsInRange(pokemons.first().id, pokemons.last().id)
                Timber.d("Save ${pokemons.size} Pokémons in DB")
                pokemonDao.insertAll(pokemons)
            }
            .subscribeBy(onSuccess = { newPokemons ->
                Timber.d("Successfully retrieved ${newPokemons.size} Pokémons from network")

                isLoadingPokemons = false
                pokemons = if (pageNumber == 0) newPokemons else pokemons + newPokemons

                pokemonsSubject.onNext(Lce.Content(this.pokemons))
            }, onError = { error ->
                Timber.e(error, "Get Pokémons from network failed")
                isLoadingPokemons = false
                pokemonsSubject.onNext(Lce.Error(error))
            })
            .addTo(compositeDisposable)
    }

    fun loadNextPokemonsPage() {
        if (isLoadingPokemons) return

        Timber.d("Load next Pokémons page")

        pokemonsCurrentPageNumber += 1

        isLoadingPokemons = true
        getPokemonsFromDB(pokemonsCurrentPageNumber)
    }

    // endregion
}
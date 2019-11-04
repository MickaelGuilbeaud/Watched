package mg.template.pokedex

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import mg.template.core.Lce
import mg.template.core.viewmodel.BaseViewModel
import mg.template.core.viewmodel.DefaultNavigationEvent
import mg.template.core.viewmodel.ErrorActionEvent
import mg.template.data.pokemon.PokemonStore

internal class PokedexViewModel(
    private val pokemonStore: PokemonStore
) : BaseViewModel<PokedexViewState, DefaultNavigationEvent, ErrorActionEvent>() {

    init {
        pokemonStore.pokemonsStream()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { lce ->
                when (lce) {
                    is Lce.Loading -> pushViewState(PokedexViewState.Loading)
                    is Lce.Content -> pushViewState(PokedexViewState.Pokemons(lce.content))
                    is Lce.Error -> pushViewState(PokedexViewState.Error(lce.error))
                }
            })
            .addTo(compositeDisposable)
    }
}
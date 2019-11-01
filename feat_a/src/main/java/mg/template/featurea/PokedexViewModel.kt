package mg.template.featurea

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import mg.template.core.viewmodel.BaseViewModel
import mg.template.core.viewmodel.DefaultNavigationEvent
import mg.template.core.viewmodel.ErrorActionEvent
import mg.template.data.pokemon.PokemonStore
import timber.log.Timber

internal class PokedexViewModel(
    private val pokemonStore: PokemonStore
) : BaseViewModel<PokedexViewState, DefaultNavigationEvent, ErrorActionEvent>() {

    init {
        pokemonStore.getPokemonsOnce()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = { pushViewState(PokedexViewState.Pokemons(it)) },
                onError = { Timber.e(it, "Get Pokemons failed") })
            .addTo(compositeDisposable)
    }
}
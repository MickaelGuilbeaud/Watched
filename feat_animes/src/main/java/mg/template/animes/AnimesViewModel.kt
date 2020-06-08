package mg.template.animes

import mg.template.core.viewmodel.BaseViewModel
import mg.template.core.viewmodel.DefaultNavigationEvent
import mg.template.core.viewmodel.ErrorActionEvent
import mg.template.data.anime.AnimeRepository

internal class AnimesViewModel(
    animesRepository: AnimeRepository
) : BaseViewModel<AnimesViewState, DefaultNavigationEvent, ErrorActionEvent>() {

    init {
        pushViewState(AnimesViewState.Loading)

        /*
        animesRepository.getSeasonAnimesStream()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { animesLce ->
                Timber.d("Received animes")

                val viewState: AnimesViewState = when (animesLce) {
                    is Lce.Loading -> if (animesLce.previousContent == null) {
                        AnimesViewState.Loading
                    } else {
                        AnimesViewState.Animes(animesLce.previousContent!!)
                    }
                    is Lce.Content -> AnimesViewState.Animes(animesLce.content)
                    is Lce.Error -> AnimesViewState.Error(animesLce.error)
                }
                pushViewState(viewState)
            }, onError = { error ->
                Timber.e(error, "Failed to retrieve animes")
                pushViewState(AnimesViewState.Error(error))
            })
            .addTo(compositeDisposable)
         */
    }
}
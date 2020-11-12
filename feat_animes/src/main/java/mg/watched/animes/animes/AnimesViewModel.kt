package mg.watched.animes.animes

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.core.viewmodel.DefaultNavigationEvent
import mg.watched.core.viewmodel.ErrorActionEvent
import mg.watched.data.anime.AnimeRepository
import timber.log.Timber

internal class AnimesViewModel(
    animesRepository: AnimeRepository
) : BaseViewModel<AnimesViewState, DefaultNavigationEvent, ErrorActionEvent>() {

    init {
        pushViewState(AnimesViewState.Loading)

        animesRepository.animePagedListStream
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { animes ->
                    Timber.d("Retrieved paged animes")
                    pushViewState(AnimesViewState.Animes(animes))
                },
                onError = { error ->
                    Timber.e(error, "Failed to retrieve animes")
                    pushViewState(AnimesViewState.Error(error))
                }
            )
            .addTo(compositeDisposable)
    }
}

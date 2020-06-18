package mg.template.animes

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import mg.template.core.viewmodel.BaseViewModel
import mg.template.core.viewmodel.DefaultNavigationEvent
import mg.template.core.viewmodel.ErrorActionEvent
import mg.template.data.anime.AnimeRepository
import timber.log.Timber

internal class AnimesViewModel(
    animesRepository: AnimeRepository
) : BaseViewModel<AnimesViewState, DefaultNavigationEvent, ErrorActionEvent>() {

    init {
        pushViewState(AnimesViewState.Loading)

        animesRepository.getAnimes()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = { animes ->
                Timber.d("Retrieved animes")
                pushViewState(AnimesViewState.Animes(animes))
            }, onError = { error ->
                Timber.e(error, "Failed to retrieve animes")
                pushViewState(AnimesViewState.Error(error))
            })
            .addTo(compositeDisposable)
    }
}
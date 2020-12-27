package mg.watched.animes.animedetail

import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import mg.watched.core.utils.SchedulerProvider
import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.data.anime.AnimeRepository
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.MyListStatus
import timber.log.Timber

class AnimeDetailViewModel(
    private val animeRepository: AnimeRepository,
    private val _anime: Anime,
    private val schedulerProvider: SchedulerProvider,
) : BaseViewModel<AnimeDetailViewState, Unit, AnimeDetailActionEvent>() {

    private var anime: Anime = _anime

    init {
        pushViewState(AnimeDetailViewState(anime))
    }

    fun updateListStatus(listStatusToUpdate: MyListStatus) {
        animeRepository.updateListStatus(anime.id, listStatusToUpdate)
            .doOnSubscribe {
                Timber.d("Update list status")
                val anime: Anime = anime.copy(myListStatus = listStatusToUpdate)
                pushViewState(AnimeDetailViewState(anime))
            }
            .observeOn(schedulerProvider.ui())
            .subscribeBy(onSuccess = { updatedListStatus ->
                Timber.d("Update list status successful")
                anime = anime.copy(myListStatus = updatedListStatus)
                pushViewState(AnimeDetailViewState(anime))
            }, onError = { error ->
                Timber.e(error, "Update list status failed")
                pushViewState(AnimeDetailViewState(anime))
                pushActionEvent(AnimeDetailActionEvent.UpdateListStatusFailed)
            })
            .addTo(compositeDisposable)
    }
}

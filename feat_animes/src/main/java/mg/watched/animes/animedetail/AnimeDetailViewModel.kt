package mg.watched.animes.animedetail

import androidx.lifecycle.viewModelScope
import mg.watched.core.utils.RResult
import mg.watched.core.utils.exhaustive
import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.data.anime.AnimeRepository
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.MyListStatus
import mg.watched.data.anime.network.models.WatchStatus
import timber.log.Timber

class AnimeDetailViewModel(
    private val animeRepository: AnimeRepository,
    _anime: Anime,
) : BaseViewModel<AnimeDetailViewState, Unit, AnimeDetailActionEvent>() {

    private var anime: Anime = _anime.copy()

    init {
        pushViewState(AnimeDetailViewState(anime))
    }

    fun addToWatchlist() {
        viewModelScope.launch {
            Timber.d("Add to watch list")
            val tempAnime: Anime = anime.copy(myListStatus = MyListStatus(0, .0, WatchStatus.PLAN_TO_WATCH))
            pushViewState(AnimeDetailViewState(tempAnime))

            val result: RResult<MyListStatus> = animeRepository.addToWatchlist(anime.id)
            when (result) {
                is RResult.Success -> {
                    Timber.d("Add to watch list successful")
                    anime = anime.copy(myListStatus = result.value)
                    pushViewState(AnimeDetailViewState(anime))
                }
                is RResult.Failure -> {
                    Timber.e(result.error, "Add to watch list failed")
                    pushViewState(AnimeDetailViewState(anime))
                    pushActionEvent(AnimeDetailActionEvent.AddToWatchlistFailed)
                }
            }.exhaustive
        }
    }

    fun updateListStatus(listStatusToUpdate: MyListStatus) {
        viewModelScope.launch {
            Timber.d("Update list status")
            val tempAnime: Anime = anime.copy(myListStatus = listStatusToUpdate)
            pushViewState(AnimeDetailViewState(tempAnime))

            val result: RResult<MyListStatus> = animeRepository.updateListStatus(anime.id, listStatusToUpdate)
            when (result) {
                is RResult.Success -> {
                    Timber.d("Update list status successful")
                    anime = anime.copy(myListStatus = result.value)
                    pushViewState(AnimeDetailViewState(anime))
                }
                is RResult.Failure -> {
                    Timber.e(result.error, "Update list status failed")
                    pushViewState(AnimeDetailViewState(anime))
                    pushActionEvent(AnimeDetailActionEvent.UpdateListStatusFailed)
                }
            }.exhaustive
        }
    }
}

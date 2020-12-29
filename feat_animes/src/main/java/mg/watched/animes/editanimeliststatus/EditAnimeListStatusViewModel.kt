package mg.watched.animes.editanimeliststatus

import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.MyListStatus
import mg.watched.data.anime.network.models.WatchStatus

class EditAnimeListStatusViewModel(
    private val anime: Anime,
) : BaseViewModel<EditAnimeListStatusViewState, EditAnimeListStatusNavigationEvent, Unit>() {

    private var nbEpisodeWatched: Int = anime.myListStatus!!.nbEpisodesWatched

    init {
        createAndPushViewState()
    }

    private fun createAndPushViewState() {
        pushViewState(EditAnimeListStatusViewState(nbEpisodeWatched, anime.nbEpisodes))
    }

    fun addWatchedEpisode(nbEpisodeToAdd: Int) {
        nbEpisodeWatched = (nbEpisodeWatched + nbEpisodeToAdd).coerceAtMost(anime.nbEpisodes)
        createAndPushViewState()
    }

    fun removeWatchedEpisode(nbEpisodeToRemove: Int) {
        nbEpisodeWatched = (nbEpisodeWatched - nbEpisodeToRemove).coerceAtLeast(0)
        createAndPushViewState()
    }

    fun applyChanges(selectedWatchStatus: WatchStatus, selectedScore: Double) {
        val updatedListStatus: MyListStatus = anime.myListStatus!!.copy(
            nbEpisodesWatched = this.nbEpisodeWatched,
            score = selectedScore,
            status = selectedWatchStatus,
        )
        pushNavigationEvent(EditAnimeListStatusNavigationEvent.GoToAnimeDetailScreen(updatedListStatus))
    }
}

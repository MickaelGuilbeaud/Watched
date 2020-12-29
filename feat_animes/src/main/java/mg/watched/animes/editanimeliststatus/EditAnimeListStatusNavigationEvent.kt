package mg.watched.animes.editanimeliststatus

import mg.watched.data.anime.network.models.MyListStatus

sealed class EditAnimeListStatusNavigationEvent {
    data class GoToAnimeDetailScreen(val updatedListStatus: MyListStatus) : EditAnimeListStatusNavigationEvent()
}

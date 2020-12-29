package mg.watched.animes.animedetail

sealed class AnimeDetailActionEvent {
    object AddToWatchlistFailed : AnimeDetailActionEvent()
    object UpdateListStatusFailed : AnimeDetailActionEvent()
}

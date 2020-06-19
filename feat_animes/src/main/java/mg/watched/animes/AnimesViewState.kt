package mg.watched.animes

import mg.watched.data.anime.network.models.Anime

internal sealed class AnimesViewState {
    object Loading : AnimesViewState()
    data class Error(val error: Throwable) : AnimesViewState()
    data class Animes(val animes: List<Anime>) : AnimesViewState()
}
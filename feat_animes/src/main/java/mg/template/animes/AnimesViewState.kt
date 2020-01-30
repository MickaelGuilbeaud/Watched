package mg.template.animes

import mg.template.data.anime.db.models.Anime

internal sealed class AnimesViewState {
    object Loading : AnimesViewState()
    data class Error(val error: Throwable) : AnimesViewState()
    data class Animes(val animes: List<Anime>) : AnimesViewState()
}
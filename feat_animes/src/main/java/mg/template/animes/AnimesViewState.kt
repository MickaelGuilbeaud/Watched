package mg.template.animes

import mg.template.data.anime.db.models.Anime

sealed class AnimesViewState {
    object Loading : AnimesViewState()
    data class Error(val error: Throwable) : AnimesViewState()
    data class Animes(val animes: List<Anime>) : AnimesViewState()
}
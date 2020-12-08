package mg.watched.animes.animedetail

import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.data.anime.network.models.Anime

class AnimeDetailViewModel(
    private val anime: Anime,
) : BaseViewModel<AnimeDetailViewState, Unit, Unit>() {

    init {
        pushViewState(AnimeDetailViewState(anime))
    }
}

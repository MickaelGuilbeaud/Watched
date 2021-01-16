package mg.watched.animes.animes

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.core.viewmodel.DefaultNavigationEvent
import mg.watched.core.viewmodel.ErrorActionEvent
import mg.watched.data.anime.AnimeRepository

internal class AnimesViewModel(
    animesRepository: AnimeRepository
) : BaseViewModel<AnimesViewState, DefaultNavigationEvent, ErrorActionEvent>() {

    init {
        pushViewState(AnimesViewState.Loading)

        viewModelScope.launch {
            animesRepository.animePagedListStream
                .collectLatest { animes ->
                    pushViewState(AnimesViewState.Animes(animes))
                }
        }
    }
}

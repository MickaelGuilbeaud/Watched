package mg.watched.animes.animes

import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
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
            animesRepository.createUserAnimesDataSourceFactory(this)
                .toLiveData(animesRepository.defaultAnimePagedListConfig)
                .asFlow()
                .collectLatest { animes ->
                    pushViewState(AnimesViewState.Animes(animes))
                }
        }
    }
}

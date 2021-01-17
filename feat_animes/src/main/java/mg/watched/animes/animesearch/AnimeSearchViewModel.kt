package mg.watched.animes.animesearch

import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.data.anime.AnimeRepository
import mg.watched.data.anime.AnimeSearchDataSourceFactory
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
internal class AnimeSearchViewModel(
    private val animesRepository: AnimeRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Main,
) : BaseViewModel<AnimeSearchViewState, Unit, Unit>() {

    // region Properties

    private val searchChannel: BroadcastChannel<String> = BroadcastChannel(Channel.CONFLATED)

    private val animeSearchDataSourceFactory: AnimeSearchDataSourceFactory =
        animesRepository.createSearchDataSourceFactory()

    // endregion

    init {
        pushViewState(AnimeSearchViewState.NoSearch)

        viewModelScope.launch(defaultDispatcher) {
            searchChannel.asFlow()
                .debounce(500)
                .distinctUntilChanged()
                .flatMapLatest { searchTerm ->
                    if (searchTerm.length < 3) {
                        pushViewState(AnimeSearchViewState.NoSearch)
                        flow {}
                    } else {
                        Timber.d("Search animes with search term $searchTerm")

                        pushViewState(AnimeSearchViewState.Loading)

                        animeSearchDataSourceFactory.search(searchTerm)
                        animeSearchDataSourceFactory.toLiveData(animesRepository.defaultAnimePagedListConfig).asFlow()
                    }
                }
                .collectLatest { animes ->
                    if (animes.isEmpty()) {
                        pushViewState(AnimeSearchViewState.NoSearchResult)
                    } else {
                        pushViewState(AnimeSearchViewState.SearchResults(animes))
                    }
                }
        }
    }

    fun searchAnimes(searchTerm: String) {
        searchChannel.offer(searchTerm)
    }
}

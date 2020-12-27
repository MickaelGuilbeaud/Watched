package mg.watched.animes.animesearch

import androidx.paging.toObservable
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import mg.watched.core.utils.DefaultSchedulerProvider
import mg.watched.core.utils.SchedulerProvider
import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.data.anime.AnimeRepository
import mg.watched.data.anime.AnimeSearchDataSourceFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

internal class AnimeSearchViewModel(
    private val animesRepository: AnimeRepository,
    private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()
) : BaseViewModel<AnimeSearchViewState, Unit, Unit>() {

    // region Properties

    private val searchTermSubject = PublishSubject.create<String>()
    private val animeSearchDataSourceFactory: AnimeSearchDataSourceFactory =
        animesRepository.createSearchDataSourceFactory()

    // endregion

    init {
        pushViewState(AnimeSearchViewState.NoSearch)

        searchTermSubject.debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(schedulerProvider.ui())
            .switchMap { searchTerm ->
                if (searchTerm.length < 3) {
                    pushViewState(AnimeSearchViewState.NoSearch)
                    Observable.never()
                } else {
                    Timber.d("Search animes with search term $searchTerm")

                    pushViewState(AnimeSearchViewState.Loading)

                    animeSearchDataSourceFactory.search(searchTerm)
                    animeSearchDataSourceFactory.toObservable(animesRepository.defaultAnimePagedListConfig)
                }
            }
            .subscribeBy(onNext = { animes ->
                if (animes.isEmpty()) {
                    pushViewState(AnimeSearchViewState.NoSearchResult)
                } else {
                    pushViewState(AnimeSearchViewState.SearchResults(animes))
                }
            }, onError = { error ->
                Timber.e(error)
                // TODO: Handle errors
            })
            .addTo(compositeDisposable)
    }

    fun searchAnimes(searchTerm: String) {
        searchTermSubject.onNext(searchTerm)
    }
}

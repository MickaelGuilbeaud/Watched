package mg.watched.animes.animesearch

import androidx.paging.toObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.data.anime.AnimeRepository
import mg.watched.data.anime.AnimeSearchDataSourceFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

internal class AnimeSearchViewModel(
    private val animesRepository: AnimeRepository
) : BaseViewModel<AnimeSearchViewState, Unit, Unit>() {

    // region Properties

    private val searchTermSubject = PublishSubject.create<String>()

    private val animeSearchDataSourceFactory: AnimeSearchDataSourceFactory =
        animesRepository.createAnimeSearchDaTaSourceFactory()
    private var animeSearchDisposable: Disposable? = null

    // endregion

    init {
        pushViewState(AnimeSearchViewState.NoSearch)

        searchTermSubject.debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { searchTerm ->
                if (searchTerm.length < 3) {
                    pushViewState(AnimeSearchViewState.NoSearch)
                } else {
                    Timber.d("Search animes with search term $searchTerm")
                    pushViewState(AnimeSearchViewState.Loading)
                    performSearch(searchTerm)
                }
            }
            .addTo(compositeDisposable)
    }

    fun searchAnimes(searchTerm: String) {
        searchTermSubject.onNext(searchTerm)
    }

    private fun performSearch(searchTerm: String) {
        animeSearchDisposable?.dispose()

        animeSearchDataSourceFactory.search(searchTerm)
        animeSearchDisposable = animeSearchDataSourceFactory.toObservable(animesRepository.defaultAnimePagedListConfig)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { animes ->
                if (animes.isEmpty()) {
                    pushViewState(AnimeSearchViewState.NoSearchResult)
                } else {
                    pushViewState(AnimeSearchViewState.SearchResults(animes))
                }
            }, onError = { error ->
                Timber.e(error)
                // TODO
            })
            .addTo(compositeDisposable)
    }
}
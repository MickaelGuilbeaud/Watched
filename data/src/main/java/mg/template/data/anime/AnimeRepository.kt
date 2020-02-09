package mg.template.data.anime

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import mg.template.core.Lce
import mg.template.data.anime.db.AnimeDao
import mg.template.data.anime.db.models.Anime
import mg.template.data.anime.network.AnimeService
import timber.log.Timber

private sealed class NetworkResourceState {
    object None : NetworkResourceState()
    object Loading : NetworkResourceState()
    object Success : NetworkResourceState()
    data class Error(val error: Throwable) : NetworkResourceState()
}

class AnimeRepository(
    private val animeDao: AnimeDao,
    private val animeService: AnimeService
) {

    // region Properties

    private val compositeDisposable = CompositeDisposable()

    // endregion

    // region Data stream

    fun getSeasonAnimesStream(): Observable<Lce<List<Anime>>> = Observables.combineLatest(
        animeDao.getAllStream(),
        networkResourceStateSubject
    ) { animes, networkResourceState ->
        if (animes.isEmpty() && networkResourceState is NetworkResourceState.None) {
            retrieveCurrentSeasonAnimes()
        }

        when (networkResourceState) {
            NetworkResourceState.None -> Lce.Content(animes)
            NetworkResourceState.Loading -> Lce.Loading(animes)
            NetworkResourceState.Success -> Lce.Content(animes)
            is NetworkResourceState.Error -> Lce.Error(networkResourceState.error, animes)
        }
    }

    // endregion

    // region Network

    private val networkResourceStateSubject: BehaviorSubject<NetworkResourceState> =
        BehaviorSubject.createDefault(NetworkResourceState.None)

    private fun retrieveCurrentSeasonAnimes() {
        animeService.getSeasonAnimes("2020", "winter")
            .doOnSubscribe {
                Timber.d("Retrieve current season animes")
                networkResourceStateSubject.onNext(NetworkResourceState.Loading)
            }
            .subscribeOn(Schedulers.io())
            .map { animesWrapper ->
                animesWrapper.anime.mapIndexed { index, anime ->
                    Anime(
                        anime.malId,
                        anime.title,
                        anime.imageUrl,
                        anime.synopsis,
                        anime.episodes,
                        anime.genres.map { genre -> genre.name },
                        anime.producers.map { producer -> producer.name },
                        anime.score,
                        index
                    )
                }
            }
            .doOnSuccess { animes ->
                animeDao.deleteAll()
                animeDao.insert(animes)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { networkResourceStateSubject.onNext(NetworkResourceState.Success) },
                onError = { error -> networkResourceStateSubject.onNext(NetworkResourceState.Error(error)) }
            )
            .addTo(compositeDisposable)
    }

    // endregion
}
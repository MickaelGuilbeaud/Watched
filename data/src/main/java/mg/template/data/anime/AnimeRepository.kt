package mg.template.data.anime

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import mg.template.core.utils.SchedulerProvider
import mg.template.data.anime.network.AnimeService
import mg.template.data.anime.network.models.Anime

private sealed class NetworkResourceState {
    object None : NetworkResourceState()
    object Loading : NetworkResourceState()
    object Success : NetworkResourceState()
    data class Error(val error: Throwable) : NetworkResourceState()
}

class AnimeRepository(
    private val animeService: AnimeService,
    private val schedulerProvider: SchedulerProvider
) {

    // region Properties

    private val compositeDisposable = CompositeDisposable()

    // endregion

    fun getAnimes(): Single<List<Anime>> = animeService.getUserAnimes()
        .subscribeOn(schedulerProvider.io())
        .map { animesWrapper ->
            animesWrapper.data.map { animeWrapper -> animeWrapper.node }
        }
}
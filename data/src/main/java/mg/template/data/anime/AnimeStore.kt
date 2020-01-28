package mg.template.data.anime

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import mg.template.data.anime.db.AnimeDao
import mg.template.data.anime.db.models.Anime
import mg.template.data.anime.network.AnimeService

class AnimeStore(
    private val animeDao: AnimeDao,
    private val animeService: AnimeService
) {

    fun getTopAnimesOnce(): Single<List<Anime>> = animeService.getSeasonAnimes("2020", "winter")
        .subscribeOn(Schedulers.io())
        .map { animesWrapper -> animesWrapper.anime }
        .map { animes ->
            animes.map { anime ->
                Anime(
                    anime.malId,
                    anime.title,
                    anime.imageUrl,
                    anime.synopsis,
                    anime.episodes,
                    anime.genres.map { genre -> genre.name },
                    anime.producers.map { producer -> producer.name },
                    anime.score
                )
            }
        }
}
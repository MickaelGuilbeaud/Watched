package mg.watched.animes.animes

import mg.watched.animes.animedetail.AnimeDetailViewModel
import mg.watched.animes.animesearch.AnimeSearchViewModel
import mg.watched.animes.editanimeliststatus.EditAnimeListStatusViewModel
import mg.watched.data.anime.network.models.Anime
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val animesDiModule = module {
    viewModel { (anime: Anime) -> AnimeDetailViewModel(get(), anime) }
    viewModel { AnimesViewModel(get()) }
    viewModel { AnimeSearchViewModel(get()) }
    viewModel { (anime: Anime) -> EditAnimeListStatusViewModel(anime) }
}

package mg.watched.animes.animes

import mg.watched.animes.animesearch.AnimeSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val animesDiModule = module {
    viewModel { AnimesViewModel(get()) }
    viewModel { AnimeSearchViewModel(get()) }
}
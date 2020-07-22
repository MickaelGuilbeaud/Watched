package mg.watched.animes.animes

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val animesDiModule = module {
    viewModel { AnimesViewModel(get()) }
}
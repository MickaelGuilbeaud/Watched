package mg.template.animes

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val animesDiModule = module {
    viewModel { AnimesViewModel(get()) }
}
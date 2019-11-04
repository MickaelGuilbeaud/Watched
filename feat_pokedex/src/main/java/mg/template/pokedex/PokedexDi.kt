package mg.template.pokedex

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokedexDiModule = module {
    viewModel { PokedexViewModel(get()) }
}
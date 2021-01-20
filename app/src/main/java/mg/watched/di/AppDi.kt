package mg.watched.di

import mg.watched.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appDiModule = module {

    factory<String>(named("baseUrl")) {
        "https://api.myanimelist.net/"
    }

    viewModel { MainViewModel(get()) }
}

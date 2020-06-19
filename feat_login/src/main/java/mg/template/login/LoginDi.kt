package mg.template.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginDiModule = module {
    viewModel { LogInViewModel(get(), get()) }
}
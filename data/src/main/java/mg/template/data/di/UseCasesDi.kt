package mg.template.data.di

import mg.template.data.usecases.LogInUseCase
import org.koin.dsl.module

val useCasesDiModule = module {

    factory<LogInUseCase> { LogInUseCase(get(), get()) }
}
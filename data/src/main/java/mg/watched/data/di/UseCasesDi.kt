package mg.watched.data.di

import mg.watched.data.usecases.LogInUseCase
import org.koin.dsl.module

val useCasesDiModule = module {

    factory<LogInUseCase> { LogInUseCase(get(), get()) }
}

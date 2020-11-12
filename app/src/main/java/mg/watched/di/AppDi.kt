package mg.watched.di

import mg.watched.core.utils.DefaultSchedulerProvider
import mg.watched.core.utils.SchedulerProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appDiModule = module {

    single<SchedulerProvider> { DefaultSchedulerProvider() }

    factory<String>(named("baseUrl")) {
        "https://api.myanimelist.net/v2/"
    }
}

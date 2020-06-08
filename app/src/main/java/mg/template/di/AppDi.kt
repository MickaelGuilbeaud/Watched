package mg.template.di

import mg.template.core.utils.DefaultSchedulerProvider
import mg.template.core.utils.SchedulerProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appDiModule = module {

    single<SchedulerProvider> { DefaultSchedulerProvider() }

    factory<String>(named("baseUrl")) {
        "https://api.myanimelist.net/v2/"
    }
}
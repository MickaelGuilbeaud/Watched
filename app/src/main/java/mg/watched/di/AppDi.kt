package mg.watched.di

import org.koin.core.qualifier.named
import org.koin.dsl.module

val appDiModule = module {

    factory<String>(named("baseUrl")) {
        "https://api.myanimelist.net/v2/"
    }
}

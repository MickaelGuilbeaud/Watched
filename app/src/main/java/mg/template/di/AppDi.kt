package mg.template.di

import mg.template.core.base.BaseApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appDiModule = module {

    factory<String>(named("baseUrl")) {
        (androidContext() as BaseApplication).buildParams.baseUrl
    }
}
package mg.watched

import mg.watched.animes.animesDiModule
import mg.watched.core.base.BaseApplication
import mg.watched.core.build.BuildParams
import mg.watched.data.di.dataDiModule
import mg.watched.data.di.useCasesDiModule
import mg.watched.di.appDiModule
import mg.watched.login.loginDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WatchedApplication : BaseApplication() {

    override val buildParams: BuildParams = BuildParamsImpl()

    override fun initDI() {
        startKoin {
            androidLogger()
            androidContext(this@WatchedApplication)
            modules(
                listOf(
                    appDiModule,
                    dataDiModule,
                    useCasesDiModule,
                    animesDiModule,
                    loginDiModule
                )
            )
        }
    }
}
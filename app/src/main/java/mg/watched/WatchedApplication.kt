package mg.watched

import mg.watched.animes.animes.animesDiModule
import mg.watched.core.base.BaseApplication
import mg.watched.data.di.dataDiModule
import mg.watched.di.appDiModule
import mg.watched.login.loginDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WatchedApplication : BaseApplication() {

    override fun initDI() {
        startKoin {
            // There is a bug in Koin 2.1.5 + Kotlin 1.4 that prevent the use of the generic logger
            // androidLogger()
            androidLogger(Level.ERROR)
            androidContext(this@WatchedApplication)
            modules(
                listOf(
                    appDiModule,
                    dataDiModule,
                    animesDiModule,
                    loginDiModule
                )
            )
        }
    }
}

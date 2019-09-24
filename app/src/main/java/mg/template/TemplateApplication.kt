package mg.template

import mg.template.core.BaseApplication
import mg.template.di.appDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TemplateApplication : BaseApplication() {

    override fun initDI() {
        startKoin {
            androidContext(this@TemplateApplication)
            modules(listOf(appDiModule))
        }
    }
}
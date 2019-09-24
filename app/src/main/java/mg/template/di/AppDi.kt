package mg.template.di

import mg.template.FeatureARouterImpl
import mg.template.core.BaseActivity
import mg.template.featurea.FeatureARouter
import org.koin.dsl.module

val appDiModule = module {
    single<FeatureARouter> { (activity: BaseActivity) -> FeatureARouterImpl(activity) }
}
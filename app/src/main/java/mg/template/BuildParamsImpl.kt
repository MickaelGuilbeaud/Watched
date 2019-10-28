package mg.template

import mg.template.core.BuildEnvironment
import mg.template.core.BuildParams
import timber.log.Timber

class BuildParamsImpl : BuildParams {

    override fun getBaseUrl(): String = BuildConfig.BASE_URL

    override fun getEnvironment(): BuildEnvironment = when (BuildConfig.FLAVOR) {
        "dev" -> BuildEnvironment.DEV
        "preprod" -> BuildEnvironment.PREPROD
        "prod" -> BuildEnvironment.PROD
        else -> {
            Timber.e("Unknown flavor: ${BuildConfig.FLAVOR}")
            BuildEnvironment.PROD
        }
    }
}
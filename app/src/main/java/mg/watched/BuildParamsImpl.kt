package mg.watched

import mg.watched.core.build.BuildEnvironment
import mg.watched.core.build.BuildParams
import timber.log.Timber

class BuildParamsImpl : BuildParams {

    override val baseUrl: String = BuildConfig.BASE_URL

    override val environment: BuildEnvironment = when (BuildConfig.FLAVOR) {
        "dev" -> BuildEnvironment.DEV
        "preprod" -> BuildEnvironment.PREPROD
        "prod" -> BuildEnvironment.PROD
        else -> {
            Timber.e("Unknown flavor: ${BuildConfig.FLAVOR}")
            BuildEnvironment.PROD
        }
    }
}

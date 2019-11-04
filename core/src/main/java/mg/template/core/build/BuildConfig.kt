package mg.template.core.build

interface BuildParams {
    val baseUrl: String
    val environment: BuildEnvironment
}

enum class BuildEnvironment {
    DEV,
    PROD,
    PREPROD
}
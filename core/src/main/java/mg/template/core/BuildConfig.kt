package mg.template.core

interface BuildParams {
    val baseUrl: String
    val environment: BuildEnvironment
}

enum class BuildEnvironment {
    DEV,
    PROD,
    PREPROD
}
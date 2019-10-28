package mg.template.core

interface BuildParams {
    fun getBaseUrl(): String
    fun getEnvironment(): BuildEnvironment
}

enum class BuildEnvironment {
    DEV,
    PROD,
    PREPROD
}
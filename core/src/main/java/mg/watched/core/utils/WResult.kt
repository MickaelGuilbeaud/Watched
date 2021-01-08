package mg.watched.core.utils

/**
 * W(atched)Result. The Result name is already used by Kotlin but is not usable for our use-cases.
 */
sealed class WResult<out T : Any?> {
    data class Success<T>(val value: T) : WResult<T>()
    data class Failure<T>(val error: Exception) : WResult<T>()
}

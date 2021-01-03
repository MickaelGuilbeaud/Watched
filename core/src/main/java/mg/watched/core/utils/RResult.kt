package mg.watched.core.utils

sealed class RResult<out T : Any?> {
    data class Success<T>(val value: T) : RResult<T>()
    data class Failure<T>(val error: Exception) : RResult<T>()
}

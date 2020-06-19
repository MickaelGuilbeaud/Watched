package mg.watched.core

sealed class Lce<T> {
    class Loading<T>(val previousContent: T? = null) : Lce<T>()
    class Content<T>(val content: T) : Lce<T>()
    class Error<T>(val error: Throwable, val previousContent: T? = null) : Lce<T>()
}
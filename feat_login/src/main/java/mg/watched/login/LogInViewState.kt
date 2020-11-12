package mg.watched.login

internal data class LogInViewState(
    val loading: Boolean = false,
    val showUsernameIsEmptyError: Boolean = false,
    val showPasswordIsEmptyError: Boolean = false
)

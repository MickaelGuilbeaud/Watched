package mg.template.login

data class LogInViewState(
    val loading: Boolean = false,
    val showUsernameIsEmptyError: Boolean = false,
    val showPasswordIsEmptyError: Boolean = false
)
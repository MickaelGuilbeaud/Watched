package mg.template.login

data class LogInViewState(
    val showInvalidEmailError: Boolean = false,
    val showPasswordIsEmptyError: Boolean = false
)
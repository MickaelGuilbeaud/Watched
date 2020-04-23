package mg.template.login

import mg.template.core.viewmodel.BaseViewModel
import timber.log.Timber

class LogInViewModel : BaseViewModel<LogInViewState, LogInNavigationEvent, LogInActionEvent>() {

    // region Properties

    private var viewState: LogInViewState = LogInViewState()
        set(value) {
            field = value
            pushViewState(field)
        }

    // endregion

    init {
        pushViewState(viewState)
    }

    private fun isEmailValid(email: String): Boolean = true

    private fun isPasswordValid(password: String): Boolean = password.isNotEmpty()

    fun logIn(email: String, password: String) {
        Timber.d("Log in with email $email and password of length ${password.length}")

        val isEmailValid: Boolean = isEmailValid(email)
        val isPasswordValid: Boolean = isPasswordValid(password)

        if (isEmailValid && isPasswordValid) {
            // TODO: Log in
        } else {
            Timber.d("Email or password are invalid")
            viewState =
                viewState.copy(showInvalidEmailError = !isEmailValid, showPasswordIsEmptyError = !isPasswordValid)
        }
    }
}
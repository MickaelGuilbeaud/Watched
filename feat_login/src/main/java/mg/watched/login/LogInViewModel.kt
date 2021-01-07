package mg.watched.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mg.watched.core.utils.WResult
import mg.watched.core.utils.exhaustive
import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.data.authentication.AuthenticationManager
import timber.log.Timber

internal class LogInViewModel(
    private val authenticationManager: AuthenticationManager,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Main,
) : BaseViewModel<LogInViewState, LogInNavigationEvent, LogInActionEvent>() {

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

    private fun isUsernameValid(username: String): Boolean = username.isNotEmpty()

    private fun isPasswordValid(password: String): Boolean = password.isNotEmpty()

    fun logIn(username: String, password: String) {
        Timber.d("Log in with username $username and password of length ${password.length}")

        val isUsernameValid: Boolean = isUsernameValid(username)
        val isPasswordValid: Boolean = isPasswordValid(password)

        if (isUsernameValid && isPasswordValid) {
            viewState = viewState.copy(showUsernameIsEmptyError = false, showPasswordIsEmptyError = false)
            logInUser(username, password)
        } else {
            Timber.d("Username or password are invalid")
            viewState =
                viewState.copy(showUsernameIsEmptyError = !isUsernameValid, showPasswordIsEmptyError = !isPasswordValid)
        }
    }

    private fun logInUser(username: String, password: String) {
        viewModelScope.launch(defaultDispatcher) {
            Timber.d("Log in")
            viewState = viewState.copy(loading = true)

            val result: WResult<Unit> = authenticationManager.authenticateUser(username, password)
            when (result) {
                is WResult.Success -> {
                    Timber.d("Log in successful")
                    viewState = viewState.copy(loading = false)
                    pushNavigationEvent(LogInNavigationEvent.GoToAnimesScreen)
                }
                is WResult.Failure -> {
                    Timber.e(result.error, "Log in failed")
                    viewState = viewState.copy(loading = false)
                    pushActionEvent(LogInActionEvent.LogInFailed)
                }
            }.exhaustive
        }
    }
}

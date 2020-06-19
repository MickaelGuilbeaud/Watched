package mg.template.login

import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import mg.template.core.utils.SchedulerProvider
import mg.template.core.viewmodel.BaseViewModel
import mg.template.data.usecases.LogInUseCase
import timber.log.Timber

internal class LogInViewModel(
    private val logInUseCase: LogInUseCase,
    private val schedulerProvider: SchedulerProvider
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
        logInUseCase.logIn(username, password)
            .doOnSubscribe {
                Timber.d("Log in")
                viewState = viewState.copy(loading = true)
            }
            .observeOn(schedulerProvider.ui())
            .subscribeBy(onComplete = {
                Timber.d("Log in successful")
                viewState = viewState.copy(loading = false)
                pushNavigationEvent(LogInNavigationEvent.GoToAnimesScreen)
            }, onError = { error ->
                Timber.e(error, "Log in failed")
                viewState = viewState.copy(loading = false)
                pushActionEvent(LogInActionEvent.LogInFailed)
            })
            .addTo(compositeDisposable)
    }
}
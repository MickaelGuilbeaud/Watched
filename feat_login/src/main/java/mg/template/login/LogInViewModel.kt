package mg.template.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import mg.template.core.viewmodel.BaseViewModel
import mg.template.data.usecases.LogInUseCase
import timber.log.Timber

class LogInViewModel(
    private val logInUseCase: LogInUseCase
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
            logInUser(username, password)
        } else {
            Timber.d("Username or password are invalid")
            viewState =
                viewState.copy(showInvalidEmailError = !isUsernameValid, showPasswordIsEmptyError = !isPasswordValid)
        }
    }

    private fun logInUser(username: String, password: String) {
        logInUseCase.logIn(username, password)
            .doOnSubscribe { Timber.d("Log in") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onComplete = {
                Timber.d("Log in successful")
                pushNavigationEvent(LogInNavigationEvent.GoToAnimesScreen)
            }, onError = { error ->
                Timber.e(error, "Log in failed")
                // TODO
            })
            .addTo(compositeDisposable)
    }
}
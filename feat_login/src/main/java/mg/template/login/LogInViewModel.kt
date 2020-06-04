package mg.template.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import mg.template.core.utils.isAValidEmail
import mg.template.core.viewmodel.BaseViewModel
import mg.template.data.auth.SessionManager
import timber.log.Timber

class LogInViewModel(
    private val sessionManager: SessionManager
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

    private fun isEmailValid(email: String): Boolean = email.isAValidEmail()

    private fun isPasswordValid(password: String): Boolean = password.isNotEmpty()

    fun logIn(email: String, password: String) {
        Timber.d("Log in with email $email and password of length ${password.length}")

        val isEmailValid: Boolean = isEmailValid(email)
        val isPasswordValid: Boolean = isPasswordValid(password)

        if (isEmailValid && isPasswordValid) {
            logInUser(email, password)
        } else {
            Timber.d("Email or password are invalid")
            viewState =
                viewState.copy(showInvalidEmailError = !isEmailValid, showPasswordIsEmptyError = !isPasswordValid)
        }
    }

    private fun logInUser(email: String, password: String) {
        sessionManager.authenticateUser(email, password)
            .doOnSubscribe { Timber.d("Log in user") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onComplete = {
                Timber.d("Log in user successful")
                // TODO
            }, onError = { error ->
                Timber.e(error, "Log in user failed")
                // TODO
            })
            .addTo(compositeDisposable)
    }
}
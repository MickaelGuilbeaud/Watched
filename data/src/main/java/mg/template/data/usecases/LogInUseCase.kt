package mg.template.data.usecases

import io.reactivex.Completable
import mg.template.data.authentication.AuthenticationManager
import mg.template.data.user.UserRepository
import timber.log.Timber

class LogInUseCase(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository
) {
    fun logIn(username: String, password: String): Completable =
        authenticationManager.authenticateUser(username, password)
            .andThen(userRepository.getUserDetail())
            .doOnSubscribe { Timber.d("Log in") }
            .doOnComplete { Timber.d("Log in successful") }
            .doOnError { error -> Timber.e(error, "Log in failed") }
}
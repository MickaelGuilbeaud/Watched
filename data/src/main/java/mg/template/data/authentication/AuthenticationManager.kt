package mg.template.data.authentication

import io.reactivex.Completable
import mg.template.core.utils.SchedulerProvider
import mg.template.data.UltimateListPreferences

class AuthenticationManager(
    private val preferences: UltimateListPreferences,
    private val authenticationService: AuthenticationService,
    private val schedulerProvider: SchedulerProvider
) {

    val accessToken: String?
        get() = preferences.accessToken

    fun authenticateUser(username: String, password: String): Completable =
        authenticationService.authenticate(username, password)
            .subscribeOn(schedulerProvider.io())
            .doOnSuccess { authResponse -> preferences.accessToken = authResponse.accessToken }
            .ignoreElement()
}
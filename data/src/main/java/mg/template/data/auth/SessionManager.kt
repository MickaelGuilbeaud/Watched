package mg.template.data.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class SessionManager(
    private val appPreferences: SharedPreferences,
    private val authService: AuthService
) {

    companion object {
        private const val USER_ACCESS_TOKEN = "user_access_token"
    }

    var userAccessToken: String?
        get() = appPreferences.getString(USER_ACCESS_TOKEN, null)
        set(value) {
            appPreferences.edit { putString(USER_ACCESS_TOKEN, value) }
        }

    fun isUserLoggedIn(): Boolean = userAccessToken != null

    fun authenticateUser(username: String, password: String): Completable =
        authService.authenticate(username, password)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { authResponse -> userAccessToken = authResponse.accessToken }
            .ignoreElement()
}
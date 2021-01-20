package mg.watched.data.authentication

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mg.watched.core.utils.WResult
import mg.watched.data.WatchedPreferences
import retrofit2.Response

class AuthenticationManager(
    private val preferences: WatchedPreferences,
    private val authenticationService: AuthenticationService,
) {

    val accessToken: String?
        get() = preferences.accessToken

    fun isUserLoggedIn(): Boolean = preferences.accessToken != null && preferences.refreshToken != null

    suspend fun authenticateUser(username: String, password: String): WResult<Unit> = withContext(Dispatchers.IO) {
        try {
            val authResponse: AuthenticationResponse = authenticationService.authenticate(username, password)

            preferences.accessToken = authResponse.accessToken
            preferences.refreshToken = authResponse.refreshToken

            WResult.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            WResult.Failure(e)
        }
    }

    fun refreshAccessToken(): Boolean {
        val response: Response<AuthenticationResponse> =
            authenticationService.refreshAccessToken(preferences.refreshToken!!).execute()
        if (response.isSuccessful) {
            val authResponse: AuthenticationResponse = response.body()!!

            preferences.accessToken = authResponse.accessToken
            preferences.refreshToken = authResponse.refreshToken

            return true
        } else {
            return false
        }
    }
}

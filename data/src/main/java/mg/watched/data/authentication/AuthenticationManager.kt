package mg.watched.data.authentication

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mg.watched.core.utils.WResult
import mg.watched.data.UltimateListPreferences

class AuthenticationManager(
    private val preferences: UltimateListPreferences,
    private val authenticationService: AuthenticationService,
) {

    val accessToken: String?
        get() = preferences.accessToken

    suspend fun authenticateUser(username: String, password: String): WResult<Unit> = withContext(Dispatchers.IO) {
        try {
            val authResponse: AuthenticationResponse = authenticationService.authenticate(username, password)
            preferences.accessToken = authResponse.accessToken
            WResult.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            WResult.Failure(e)
        }
    }
}

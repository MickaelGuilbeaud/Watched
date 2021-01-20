package mg.watched.data.authentication

import com.squareup.moshi.Json

data class AuthenticationResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String,
)

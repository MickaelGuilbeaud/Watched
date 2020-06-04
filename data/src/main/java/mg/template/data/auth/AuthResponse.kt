package mg.template.data.auth

import com.squareup.moshi.Json

data class AuthResponse(
    @Json(name = "access_token") val accessToken: String
)
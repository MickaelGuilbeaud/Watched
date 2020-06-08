package mg.template.data.authentication

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationService {

    @POST("auth/token")
    @FormUrlEncoded
    fun authenticate(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password",
        @Field("client_id") malClientId: String = "6114d00ca681b7701d1e15fe11a4987e"
    ): Single<AuthenticationResponse>
}
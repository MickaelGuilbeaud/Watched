package mg.watched.data.user.network

import io.reactivex.Single
import mg.watched.data.user.User
import retrofit2.http.GET

interface UserService {

    @GET("users/@me")
    fun getUserDetail(): Single<User>
}
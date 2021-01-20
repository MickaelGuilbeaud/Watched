package mg.watched.data.user

import mg.watched.data.WatchedPreferences
import mg.watched.data.user.network.UserService

class UserRepository(
    private val userService: UserService,
    private val preferences: WatchedPreferences,
) {

    val user: User?
        get() = preferences.user

    /*
    fun getUserDetail(): Completable = userService.getUserDetail()
        .subscribeOn(schedulerProvider.io())
        .doOnSubscribe { Timber.d("Get user detail") }
        .doOnSuccess { user ->
            Timber.d("Retrieved user detail successfully")
            preferences.user = user
        }
        .ignoreElement()
     */
}

package mg.watched.data.user

import io.reactivex.Completable
import mg.watched.core.utils.SchedulerProvider
import mg.watched.data.UltimateListPreferences
import mg.watched.data.user.network.UserService
import timber.log.Timber

class UserRepository(
    private val userService: UserService,
    private val preferences: UltimateListPreferences,
    private val schedulerProvider: SchedulerProvider
) {

    val user: User?
        get() = preferences.user

    fun getUserDetail(): Completable = userService.getUserDetail()
        .subscribeOn(schedulerProvider.io())
        .doOnSubscribe { Timber.d("Get user detail") }
        .doOnSuccess { user ->
            Timber.d("Retrieved user detail successfully")
            preferences.user = user
        }
        .ignoreElement()
}
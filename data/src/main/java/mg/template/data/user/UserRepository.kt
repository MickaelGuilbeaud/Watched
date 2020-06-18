package mg.template.data.user

import io.reactivex.Completable
import mg.template.core.utils.SchedulerProvider
import mg.template.data.UltimateListPreferences
import mg.template.data.user.network.UserService
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
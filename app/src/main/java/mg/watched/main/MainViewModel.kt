package mg.watched.main

import mg.watched.core.viewmodel.BaseViewModel
import mg.watched.data.authentication.AuthenticationManager

class MainViewModel(
    private val authenticationManager: AuthenticationManager,
) : BaseViewModel<Unit, MainNavigationEvent, MainActionEvent>() {

    init {
        if (authenticationManager.isUserLoggedIn()) {
            pushNavigationEvent(MainNavigationEvent.GoToAnimesScreen)
        } else {
            pushNavigationEvent(MainNavigationEvent.GoToLogInScreen)
        }
    }
}

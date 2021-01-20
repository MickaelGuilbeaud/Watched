package mg.watched.main

sealed class MainNavigationEvent {
    object GoToLogInScreen : MainNavigationEvent()
    object GoToAnimesScreen : MainNavigationEvent()
}

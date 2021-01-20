package mg.watched.main

sealed class MainActionEvent {
    object SessionExpired : MainActionEvent()
}

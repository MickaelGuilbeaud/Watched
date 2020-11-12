package mg.watched.login

internal sealed class LogInActionEvent {
    object LogInFailed : LogInActionEvent()
}

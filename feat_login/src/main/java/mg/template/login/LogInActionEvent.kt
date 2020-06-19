package mg.template.login

sealed class LogInActionEvent {
    object LogInFailed : LogInActionEvent()
}
package mg.template.login

internal sealed class LogInActionEvent {
    object LogInFailed : LogInActionEvent()
}
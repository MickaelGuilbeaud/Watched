package mg.watched.core.viewmodel

/**
 * Default navigation event for BaseViewModels that don't require a specific navigation.
 */
sealed class DefaultNavigationEvent {
    object GoToPreviousScreen : DefaultNavigationEvent()
    object GoToNextScreen : DefaultNavigationEvent()
}

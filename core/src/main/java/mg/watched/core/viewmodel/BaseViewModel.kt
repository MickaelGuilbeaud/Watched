package mg.watched.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<ViewState, NavigationEvent, ActionEvent> : ViewModel() {

    // region Properties

    private val viewStateLiveData = MutableLiveData<ViewState>()
    private val navigationLiveData = MutableLiveData<Event<NavigationEvent>>()
    private val actionLiveData = MutableLiveData<Event<ActionEvent>>()

    protected val compositeDisposable = CompositeDisposable()

    // endregion

    // region Lifecycle

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    // endregion

    // region ViewState

    fun viewStates(): LiveData<ViewState> = viewStateLiveData

    protected fun pushViewState(viewState: ViewState) {
        viewStateLiveData.postValue(viewState)
    }

    // endregion

    // region Navigation events

    fun navigationEvents(): LiveData<Event<NavigationEvent>> = navigationLiveData

    protected fun pushNavigationEvent(navigationEvent: NavigationEvent) {
        navigationLiveData.postValue(Event(navigationEvent))
    }

    // endregion

    // region Action events

    fun actionEvents(): LiveData<Event<ActionEvent>> = actionLiveData

    protected fun pushActionEvent(actionEvent: ActionEvent) {
        actionLiveData.postValue(Event(actionEvent))
    }

    // endregion
}
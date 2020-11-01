package mg.watched.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_log_in.*
import mg.watched.core.base.BaseFragment
import mg.watched.core.requireFullScreenLoadingHolder
import mg.watched.core.utils.exhaustive
import mg.watched.core.utils.hideKeyboard
import mg.watched.core.viewmodel.observeEvents
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : BaseFragment(R.layout.fragment_log_in) {

    companion object {
        fun newInstance(): LogInFragment = LogInFragment()
    }

    // region Properties

    private val viewModel: LogInViewModel by viewModel()

    // endregion

    // region Lifecycle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        viewModel.viewStates().observe(viewLifecycleOwner) { bindViewState(it) }
        viewModel.navigationEvents().observeEvents(viewLifecycleOwner) { handleNavigationEvent(it) }
        viewModel.actionEvents().observeEvents(viewLifecycleOwner) { handleActionEvent(it) }
    }

    private fun initUI() {
        etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                logIn()
                true
            } else {
                false
            }
        }

        btnLogIn.setOnClickListener { logIn() }
    }

    private fun logIn() {
        hideKeyboard()

        val username: String = etUsername.text.toString().trim()
        val password: String = etPassword.text.toString().trim()
        viewModel.logIn(username, password)
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: LogInViewState) {
        requireFullScreenLoadingHolder().showLoading(viewState.loading)

        val usernameFieldError: String = if (viewState.showUsernameIsEmptyError) {
            requireContext().getString(R.string.log_in_error_empty_username)
        } else {
            ""
        }
        tilUsername.error = usernameFieldError

        val passwordFieldError: String = if (viewState.showPasswordIsEmptyError) {
            requireContext().getString(R.string.log_in_error_empty_password)
        } else {
            ""
        }
        tilPassword.error = passwordFieldError
    }

    private fun handleNavigationEvent(navigationEvent: LogInNavigationEvent) {
        when (navigationEvent) {
            LogInNavigationEvent.GoToAnimesScreen -> requireLoginRouter().routeToAnimesScreen()
        }.exhaustive
    }

    private fun handleActionEvent(actionEvent: LogInActionEvent) {
        when (actionEvent) {
            LogInActionEvent.LogInFailed ->
                Snackbar.make(requireView(), R.string.log_in_unknown_error, Snackbar.LENGTH_LONG).show()
        }.exhaustive
    }

    // endregion
}
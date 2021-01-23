package mg.watched.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import mg.watched.core.base.BaseFragment
import mg.watched.core.base.requireWatchedActivity
import mg.watched.core.utils.exhaustive
import mg.watched.core.utils.hideKeyboard
import mg.watched.core.viewmodel.observeEvents
import mg.watched.login.databinding.LogInFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : BaseFragment(R.layout.log_in_fragment) {

    companion object {
        fun newInstance(): LogInFragment = LogInFragment()
    }

    // region Properties

    private val binding: LogInFragmentBinding by viewBinding()
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

    override fun onStart() {
        super.onStart()
        requireWatchedActivity().showBottomNavigationView(false)
    }

    private fun initUI() {
        binding.etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                logIn()
                true
            } else {
                false
            }
        }

        binding.btnLogIn.setOnClickListener { logIn() }
    }

    private fun logIn() {
        hideKeyboard()

        val username: String = binding.etUsername.text.toString().trim()
        val password: String = binding.etPassword.text.toString().trim()
        viewModel.logIn(username, password)
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: LogInViewState) {
        requireWatchedActivity().showFullScreenLoading(viewState.loading)

        val usernameFieldError: String = if (viewState.showUsernameIsEmptyError) {
            requireContext().getString(R.string.log_in_error_empty_username)
        } else {
            ""
        }
        binding.tilUsername.error = usernameFieldError

        val passwordFieldError: String = if (viewState.showPasswordIsEmptyError) {
            requireContext().getString(R.string.log_in_error_empty_password)
        } else {
            ""
        }
        binding.tilPassword.error = passwordFieldError
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

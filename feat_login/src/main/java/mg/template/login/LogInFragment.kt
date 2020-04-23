package mg.template.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_log_in.*
import mg.template.core.base.BaseFragment
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
    }

    private fun initUI() {
        etPassword.setOnEditorActionListener { v, actionId, event ->
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
        val email: String = etEmail.text.toString().trim()
        val password: String = etPassword.text.toString().trim()
        viewModel.logIn(email, password)
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: LogInViewState) {
        val emailFieldError: String = if (viewState.showInvalidEmailError) {
            requireContext().getString(R.string.log_in_error_invalid_email)
        } else {
            ""
        }
        tilEmail.error = emailFieldError

        val passwordFieldError: String = if (viewState.showPasswordIsEmptyError) {
            requireContext().getString(R.string.log_in_error_empty_password)
        } else {
            ""
        }
        tilPassword.error = passwordFieldError
    }

    // endregion
}
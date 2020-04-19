package mg.template.login

import android.os.Bundle
import android.view.View
import mg.template.core.base.BaseFragment

class LogInFragment : BaseFragment(R.layout.fragment_log_in) {

    companion object {
        fun newInstance(): LogInFragment = LogInFragment()
    }

    // region Properties

    // endregion

    // region Lifecycle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    // endregion
}
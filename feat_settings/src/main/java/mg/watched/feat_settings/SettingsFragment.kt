package mg.watched.feat_settings

import by.kirich1409.viewbindingdelegate.viewBinding
import mg.watched.core.base.BaseFragment
import mg.watched.feat_settings.databinding.SettingsFragmentBinding

class SettingsFragment : BaseFragment(R.layout.settings_fragment) {

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    // region Properties

    private val binding: SettingsFragmentBinding by viewBinding()

    // endregion
}

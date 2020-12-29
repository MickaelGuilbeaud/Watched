package mg.watched.feat_mangas.list

import by.kirich1409.viewbindingdelegate.viewBinding
import mg.watched.core.base.BaseFragment
import mg.watched.feat_mangas.R
import mg.watched.feat_mangas.databinding.MangasFragmentBinding

class MangasFragment : BaseFragment(R.layout.mangas_fragment) {

    companion object {
        fun newInstance(): MangasFragment = MangasFragment()
    }

    // region Properties

    private val binding: MangasFragmentBinding by viewBinding()

    // endregion
}

package mg.template.animes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_animes.*
import mg.template.core.base.BaseFragment
import mg.template.core.utils.exhaustive
import mg.template.core.utils.toPx
import mg.template.design.MarginItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimesFragment : BaseFragment(AnimesFragment::class.java.simpleName) {

    companion object {
        fun newInstance() = AnimesFragment()
    }

    private val viewModel: AnimesViewModel by viewModel()

    private val animeAdapter = AnimeAdapter()

    // region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_animes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.viewStates().observe(viewLifecycleOwner) { bindViewState(it) }
    }

    private fun initUI() {
        toolbar.title = getString(R.string.animes_title)

        rvAnimes.setHasFixedSize(true)
        rvAnimes.adapter = animeAdapter
        rvAnimes.addItemDecoration(MarginItemDecoration(10.toPx(requireContext())))
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: AnimesViewState) {
        when (viewState) {
            AnimesViewState.Loading -> {
                pbLoading.isVisible = true
                tvError.isVisible = false
                rvAnimes.isVisible = false
            }
            is AnimesViewState.Error -> {
                pbLoading.isVisible = false
                tvError.isVisible = true
                rvAnimes.isVisible = false
            }
            is AnimesViewState.Animes -> {
                pbLoading.isVisible = false
                tvError.isVisible = false
                rvAnimes.isVisible = true

                animeAdapter.submitList(viewState.animes)
            }
        }.exhaustive
    }

    // endregion
}
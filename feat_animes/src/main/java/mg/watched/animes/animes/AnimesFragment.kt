package mg.watched.animes.animes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_animes.*
import mg.watched.animes.R
import mg.watched.core.base.BaseFragment
import mg.watched.core.utils.exhaustive
import mg.watched.core.utils.toPx
import mg.watched.design.MarginItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimesFragment : BaseFragment(R.layout.fragment_animes) {

    companion object {
        fun newInstance() = AnimesFragment()
    }

    private val viewModel: AnimesViewModel by viewModel()

    private val animeAdapter = AnimeAdapter { anime ->
        requireAnimesRouter().routeToAnimeDetailScreen(anime)
    }

    // region Lifecycle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.viewStates().observe(viewLifecycleOwner) { bindViewState(it) }
    }

    private fun initUI() {
        toolbar.title = getString(R.string.animes_title)

        rvAnimes.setHasFixedSize(true)
        rvAnimes.adapter = animeAdapter
        rvAnimes.addItemDecoration(MarginItemDecoration(12.toPx(requireContext())))
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
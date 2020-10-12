package mg.watched.animes.animes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_animes.*
import mg.watched.animes.R
import mg.watched.animes.animedetail.AnimeDetailFragment
import mg.watched.animes.animesearch.AnimeSearchFragment
import mg.watched.animes.utils.AnimeAnimations
import mg.watched.core.base.BaseFragment
import mg.watched.core.requireFragmentContainerProvider
import mg.watched.core.utils.exhaustive
import mg.watched.core.utils.toPx
import mg.watched.design.MarginItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimesFragment : BaseFragment(R.layout.fragment_animes) {

    companion object {
        fun newInstance(): AnimesFragment = AnimesFragment()
    }

    // region Properties

    private val viewModel: AnimesViewModel by viewModel()

    private val animeAdapter = AnimeAdapter { anime, view ->
        val fragment = AnimeDetailFragment.newInstance(anime)
        parentFragmentManager.commit {
            addSharedElement(view, AnimeAnimations.getAnimeMasterDetailTransitionName(anime))
            addToBackStack(null)
            replace(requireFragmentContainerProvider().getFragmentContainerId(), fragment)
        }
    }

    // endregion

    // region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
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
        rvAnimes.addItemDecoration(MarginItemDecoration(12.toPx(requireContext())))

        fabAddAnime.setOnClickListener {
            parentFragmentManager.commit {
                addSharedElement(fabAddAnime, "transition_anime_search")
                replace(requireFragmentContainerProvider().getFragmentContainerId(), AnimeSearchFragment.newInstance())
                addToBackStack(null)
            }
        }
    }

    override fun onDestroyView() {
        rvAnimes.adapter = null
        super.onDestroyView()
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: AnimesViewState) {
        when (viewState) {
            AnimesViewState.Loading -> {
                pbLoading.isVisible = true
                tvError.isVisible = false
                rvAnimes.isVisible = false
                fabAddAnime.isVisible = false
            }
            is AnimesViewState.Error -> {
                pbLoading.isVisible = false
                tvError.isVisible = true
                rvAnimes.isVisible = false
                fabAddAnime.isVisible = false
            }
            is AnimesViewState.Animes -> {
                pbLoading.isVisible = false
                tvError.isVisible = false
                rvAnimes.isVisible = true
                fabAddAnime.isVisible = true

                animeAdapter.submitList(viewState.animes)
            }
        }.exhaustive
    }

    // endregion
}
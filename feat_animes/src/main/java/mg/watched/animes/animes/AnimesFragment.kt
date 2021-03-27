package mg.watched.animes.animes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import com.google.android.material.transition.Hold
import mg.watched.animes.R
import mg.watched.animes.animedetail.AnimeDetailFragment
import mg.watched.animes.animesearch.AnimeSearchFragment
import mg.watched.animes.databinding.AnimesFragmentBinding
import mg.watched.animes.utils.AnimeAnimations
import mg.watched.core.base.BaseFragment
import mg.watched.core.base.requireWatchedActivity
import mg.watched.core.utils.exhaustive
import mg.watched.core.utils.toPx
import mg.watched.design.MarginItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimesFragment : BaseFragment(R.layout.animes_fragment) {

    companion object {
        fun newInstance(): AnimesFragment = AnimesFragment()
    }

    // region Properties

    private val viewModel: AnimesViewModel by viewModel()

    // endregion

    // region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding: AnimesFragmentBinding = AnimesFragmentBinding.bind(requireView())
        val animeAdapter = AnimeAdapter { anime, animeView ->
            val fragment = AnimeDetailFragment.newInstance(anime)
            parentFragmentManager.commit {
                addSharedElement(animeView, AnimeAnimations.getAnimeMasterDetailTransitionName(anime))
                addToBackStack(null)
                replace(requireWatchedActivity().getFragmentContainerId(), fragment)
            }
        }

        initUI(binding, animeAdapter)

        viewModel.viewStates().observe(viewLifecycleOwner) { bindViewState(it, binding, animeAdapter) }
    }

    private fun initUI(binding: AnimesFragmentBinding, animeAdapter: AnimeAdapter) {
        binding.rvAnimes.setHasFixedSize(true)
        binding.rvAnimes.adapter = animeAdapter
        binding.rvAnimes.addItemDecoration(MarginItemDecoration(24.toPx(requireContext())))

        binding.fabAddAnime.setOnClickListener {
            parentFragmentManager.commit {
                addSharedElement(binding.fabAddAnime, "transition_anime_search")
                replace(requireWatchedActivity().getFragmentContainerId(), AnimeSearchFragment.newInstance())
                addToBackStack(null)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requireWatchedActivity().showBottomNavigationView(true)
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: AnimesViewState, binding: AnimesFragmentBinding, animeAdapter: AnimeAdapter) {
        binding.pbLoading.isVisible = viewState is AnimesViewState.Loading
        binding.tvError.isVisible = viewState is AnimesViewState.Error
        binding.rvAnimes.isVisible = viewState is AnimesViewState.Animes
        binding.fabAddAnime.isVisible = viewState is AnimesViewState.Animes

        when (viewState) {
            AnimesViewState.Loading -> Unit
            is AnimesViewState.Error -> Unit
            is AnimesViewState.Animes -> {
                animeAdapter.submitList(viewState.animes)
            }
        }.exhaustive
    }

    // endregion
}

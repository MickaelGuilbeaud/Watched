package mg.watched.animes.animes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.Hold
import mg.watched.animes.R
import mg.watched.animes.animedetail.AnimeDetailFragment
import mg.watched.animes.animesearch.AnimeSearchFragment
import mg.watched.animes.databinding.AnimesFragmentBinding
import mg.watched.animes.utils.AnimeAnimations
import mg.watched.core.base.BaseFragment
import mg.watched.core.requireFragmentContainerProvider
import mg.watched.core.utils.exhaustive
import mg.watched.core.utils.toPx
import mg.watched.design.MarginItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimesFragment : BaseFragment(R.layout.animes_fragment) {

    companion object {
        fun newInstance(): AnimesFragment = AnimesFragment()
    }

    // region Properties

    private val binding: AnimesFragmentBinding by viewBinding()
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
        binding.rvAnimes.setHasFixedSize(true)
        binding.rvAnimes.adapter = animeAdapter
        binding.rvAnimes.addItemDecoration(MarginItemDecoration(24.toPx(requireContext())))

        binding.fabAddAnime.setOnClickListener {
            parentFragmentManager.commit {
                addSharedElement(binding.fabAddAnime, "transition_anime_search")
                replace(requireFragmentContainerProvider().getFragmentContainerId(), AnimeSearchFragment.newInstance())
                addToBackStack(null)
            }
        }
    }

    override fun onDestroyView() {
        binding.rvAnimes.adapter = null
        super.onDestroyView()
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: AnimesViewState) {
        when (viewState) {
            AnimesViewState.Loading -> {
                binding.pbLoading.isVisible = true
                binding.tvError.isVisible = false
                binding.rvAnimes.isVisible = false
                binding.fabAddAnime.isVisible = false
            }
            is AnimesViewState.Error -> {
                binding.pbLoading.isVisible = false
                binding.tvError.isVisible = true
                binding.rvAnimes.isVisible = false
                binding.fabAddAnime.isVisible = false
            }
            is AnimesViewState.Animes -> {
                binding.pbLoading.isVisible = false
                binding.tvError.isVisible = false
                binding.rvAnimes.isVisible = true
                binding.fabAddAnime.isVisible = true

                animeAdapter.submitList(viewState.animes)
            }
        }.exhaustive
    }

    // endregion
}

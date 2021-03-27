package mg.watched.animes.animesearch

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform
import mg.watched.animes.R
import mg.watched.animes.animedetail.AnimeDetailFragment
import mg.watched.animes.animes.AnimeAdapter
import mg.watched.animes.databinding.AnimeSearchFragmentBinding
import mg.watched.animes.utils.AnimeAnimations
import mg.watched.core.base.BaseFragment
import mg.watched.core.base.requireWatchedActivity
import mg.watched.core.utils.exhaustive
import mg.watched.core.utils.hideKeyboard
import mg.watched.core.utils.toPx
import mg.watched.design.MarginItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeSearchFragment : BaseFragment(R.layout.anime_search_fragment) {

    companion object {
        fun newInstance(): AnimeSearchFragment = AnimeSearchFragment()
    }

    // region Properties

    private val viewModel: AnimeSearchViewModel by viewModel()

    // endregion

    // region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            containerColor = MaterialColors.getColor(requireActivity(), android.R.attr.windowBackground, "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding: AnimeSearchFragmentBinding = AnimeSearchFragmentBinding.bind(requireView())
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

    private fun initUI(binding: AnimeSearchFragmentBinding, animeAdapter: AnimeAdapter) {
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        binding.etSearch.doOnTextChanged { text, _, _, _ -> viewModel.searchAnimes(text.toString().trim()) }

        binding.rvAnimes.setHasFixedSize(true)
        binding.rvAnimes.adapter = animeAdapter
        binding.rvAnimes.addItemDecoration(MarginItemDecoration(12.toPx(requireContext())))
        binding.rvAnimes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                hideKeyboard()
            }
        })
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(
        viewState: AnimeSearchViewState,
        binding: AnimeSearchFragmentBinding,
        animeAdapter: AnimeAdapter,
    ) {
        when (viewState) {
            AnimeSearchViewState.NoSearch -> {
                binding.tvNoSearch.isVisible = true
                binding.pbLoading.isVisible = false
                binding.tvNoSearchResult.isVisible = false
                binding.rvAnimes.isVisible = false
            }
            AnimeSearchViewState.Loading -> {
                binding.tvNoSearch.isVisible = false
                binding.pbLoading.isVisible = true
                binding.tvNoSearchResult.isVisible = false
                binding.rvAnimes.isVisible = false
            }
            AnimeSearchViewState.NoSearchResult -> {
                binding.tvNoSearch.isVisible = false
                binding.pbLoading.isVisible = false
                binding.tvNoSearchResult.isVisible = true
                binding.rvAnimes.isVisible = false
            }
            is AnimeSearchViewState.SearchResults -> {
                binding.tvNoSearch.isVisible = false
                binding.pbLoading.isVisible = false
                binding.tvNoSearchResult.isVisible = false
                binding.rvAnimes.isVisible = true

                animeAdapter.submitList(viewState.animes)
            }
        }.exhaustive
    }

    // endregion
}

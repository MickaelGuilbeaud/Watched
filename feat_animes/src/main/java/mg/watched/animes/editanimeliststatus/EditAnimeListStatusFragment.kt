package mg.watched.animes.editanimeliststatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import mg.watched.animes.R
import mg.watched.animes.databinding.EditAnimeListStatusFragmentBinding
import mg.watched.core.utils.exhaustive
import mg.watched.core.utils.withArguments
import mg.watched.core.viewmodel.observeEvents
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.MyListStatus
import mg.watched.data.anime.network.models.WatchStatus
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.InvalidParameterException

class EditAnimeListStatusFragment : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_ANIME = "arg_anime"

        fun newInstance(anime: Anime): EditAnimeListStatusFragment = EditAnimeListStatusFragment().withArguments(
            ARG_ANIME to anime
        )
    }

    private val viewModel: EditAnimeListStatusViewModel by viewModel()
    private val binding: EditAnimeListStatusFragmentBinding by viewBinding()

    private val anime: Anime
        get() = requireArguments().getParcelable(ARG_ANIME)!!

    var callback: ((MyListStatus) -> Unit)? = null

    // region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.edit_anime_list_status_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        bindAnime(anime)

        viewModel.viewStates().observe(viewLifecycleOwner) { bindViewState(it) }
        viewModel.navigationEvents().observeEvents(viewLifecycleOwner) { handleNavigationEvent(it) }
    }

    private fun initUI() {
        val watchStatuses: List<String> = listOf(
            getString(R.string.watch_status_watching),
            getString(R.string.watch_status_completed),
            getString(R.string.watch_status_on_hold),
            getString(R.string.watch_status_dropped),
            getString(R.string.watch_status_plan_to_watch),
        )
        val watchStatusesAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            watchStatuses,
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spWatchStatus.adapter = watchStatusesAdapter

        val ratings: List<String> = listOf(
            getString(R.string.anime_rating_ten),
            getString(R.string.anime_rating_nine),
            getString(R.string.anime_rating_eight),
            getString(R.string.anime_rating_seven),
            getString(R.string.anime_rating_six),
            getString(R.string.anime_rating_five),
            getString(R.string.anime_rating_four),
            getString(R.string.anime_rating_three),
            getString(R.string.anime_rating_two),
            getString(R.string.anime_rating_one),
            getString(R.string.anime_rating_none),
        )
        val ratingsAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ratings,
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spRating.adapter = ratingsAdapter

        binding.btnApplyChanges.setOnClickListener { applyChanges() }
        binding.btnRemoveEpisode.setOnClickListener { viewModel.removeWatchedEpisode(1) }
        binding.btnAddEpisode.setOnClickListener { viewModel.addWatchedEpisode(1) }
    }

    private fun applyChanges() {
        val selectedWatchStatus: WatchStatus = when (binding.spWatchStatus.selectedItemPosition) {
            0 -> WatchStatus.WATCHING
            1 -> WatchStatus.COMPLETED
            2 -> WatchStatus.ON_HOLD
            3 -> WatchStatus.DROPPED
            4 -> WatchStatus.PLAN_TO_WATCH
            else -> throw InvalidParameterException(
                "Invalid watch status spinner position: ${binding.spWatchStatus.selectedItemPosition}"
            )
        }
        val selectedRating: Double = when (binding.spRating.selectedItemPosition) {
            0 -> 10.0
            1 -> 9.0
            2 -> 8.0
            3 -> 7.0
            4 -> 6.0
            5 -> 5.0
            6 -> 4.0
            7 -> 3.0
            8 -> 2.0
            9 -> 1.0
            10 -> 0.0
            else -> throw InvalidParameterException(
                "Invalid rating spinner position: ${binding.spRating.selectedItemPosition}"
            )
        }
        viewModel.applyChanges(selectedWatchStatus, selectedRating)
    }

    // endregion

    // region ViewStates, NavigationEvents and ActionEvents

    private fun bindViewState(viewState: EditAnimeListStatusViewState) {
        binding.tvEpisodeProgress.text = resources.getQuantityString(
            R.plurals.edit_anime_list_status_episode_progress,
            viewState.nbEpisodesTotal,
            viewState.nbEpisodesWatched,
            viewState.nbEpisodesTotal
        )
    }

    private fun handleNavigationEvent(navigationEvent: EditAnimeListStatusNavigationEvent) {
        when (navigationEvent) {
            is EditAnimeListStatusNavigationEvent.GoToAnimeDetailScreen -> {
                callback?.invoke(navigationEvent.updatedListStatus)
                requireActivity().onBackPressed()
            }
        }.exhaustive
    }

    // endregion

    // region Anime

    private fun bindAnime(anime: Anime) {
        val watchStatusPosition: Int = when (anime.myListStatus!!.status) {
            WatchStatus.COMPLETED -> 1
            WatchStatus.DROPPED -> 3
            WatchStatus.ON_HOLD -> 2
            WatchStatus.PLAN_TO_WATCH -> 4
            WatchStatus.WATCHING -> 0
        }
        binding.spWatchStatus.setSelection(watchStatusPosition)

        val ratingPosition: Int = 10 - anime.myListStatus!!.score.toInt()
        binding.spRating.setSelection(ratingPosition)
    }

    // endregion
}

package mg.watched.animes.animedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_edit_anime_list_status.*
import mg.watched.animes.R
import mg.watched.core.utils.withArguments
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.WatchStatus

class EditAnimeListStatusFragment : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_ANIME = "arg_anime"

        fun newInstance(anime: Anime): EditAnimeListStatusFragment = EditAnimeListStatusFragment().withArguments(
            ARG_ANIME to anime
        )
    }

    private val anime: Anime
        get() = requireArguments().getParcelable(ARG_ANIME)!!

    // region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_edit_anime_list_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        bindAnime(anime)
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
        spWatchStatus.adapter = watchStatusesAdapter

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
        spRating.adapter = ratingsAdapter

        btnApplyChanges.setOnClickListener { /* TODO */ }
        btnRemoveEpisode.setOnClickListener { /* TODO */ }
        btnAddEpisode.setOnClickListener { /* TODO */ }
    }

    private fun bindAnime(anime: Anime) {
        val watchStatusPosition: Int = when (anime.myListStatus!!.status) {
            WatchStatus.COMPLETED -> 1
            WatchStatus.DROPPED -> 3
            WatchStatus.ON_HOLD -> 2
            WatchStatus.PLAN_TO_WATCH -> 4
            WatchStatus.WATCHING -> 0
        }
        spWatchStatus.setSelection(watchStatusPosition)

        tvEpisodeProgress.text = resources.getQuantityString(
            R.plurals.edit_anime_list_status_episode_progress,
            anime.nbEpisodes,
            anime.myListStatus!!.nbEpisodesWatched,
            anime.nbEpisodes
        )

        val ratingPosition: Int = 10 - anime.myListStatus!!.score.toInt()
        spRating.setSelection(ratingPosition)
    }

    // endregion
}

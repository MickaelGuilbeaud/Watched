package mg.watched.animes.animedetail

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.anime_detail_watch_status.*
import kotlinx.android.synthetic.main.fragment_anime_detail.*
import mg.watched.animes.R
import mg.watched.animes.utils.AnimeAnimations
import mg.watched.animes.utils.formatKindSeasonAiring
import mg.watched.animes.utils.formatRating
import mg.watched.core.base.BaseFragment
import mg.watched.core.utils.withArguments
import mg.watched.data.anime.network.models.AlternativeTitles
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.WatchStatus

class AnimeDetailFragment : BaseFragment(R.layout.fragment_anime_detail) {

    companion object {
        private const val ARG_ANIME: String = "arg_anime"

        fun newInstance(anime: Anime): AnimeDetailFragment = AnimeDetailFragment().withArguments(
            ARG_ANIME to anime
        )
    }

    private val anime: Anime
        get() = requireArguments().getParcelable(ARG_ANIME)!!

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

        val anime: Anime = anime
        requireView().transitionName = AnimeAnimations.getAnimeMasterDetailTransitionName(anime)

        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        tvTitle.text = anime.title
        tvKindSeasonAiring.text = anime.formatKindSeasonAiring(requireContext())

        Glide.with(ivIllustration)
            .load(anime.mainPicture.mediumUrl)
            .into(ivIllustration)

        @StringRes val watchStatusTextResId: Int
        @DrawableRes val watchStatusDrawableResId: Int
        when (anime.myListStatus.status) {
            WatchStatus.COMPLETED -> {
                watchStatusTextResId = R.string.anime_detail_watch_status_completed
                watchStatusDrawableResId = R.drawable.ic_check_24_color_on_background
            }
            WatchStatus.DROPPED -> {
                watchStatusTextResId = R.string.anime_detail_watch_status_dropped
                watchStatusDrawableResId = R.drawable.ic_delete_24_color_on_background
            }
            WatchStatus.ON_HOLD -> {
                watchStatusTextResId = R.string.anime_detail_watch_status_on_hold
                watchStatusDrawableResId = R.drawable.ic_pause_24_color_on_background
            }
            WatchStatus.PLAN_TO_WATCH -> {
                watchStatusTextResId = R.string.anime_detail_watch_status_plan_to_watch
                watchStatusDrawableResId = R.drawable.ic_format_list_bulleted_24_color_on_background
            }
            WatchStatus.WATCHING -> {
                watchStatusTextResId = R.string.anime_detail_watch_status_watching
                watchStatusDrawableResId = R.drawable.ic_play_arrow_24_color_on_background
            }
        }
        tvWatchStatus.setText(watchStatusTextResId)
        tvWatchStatus.setCompoundDrawablesWithIntrinsicBounds(0, watchStatusDrawableResId, 0, 0)

        tvRating.text = anime.myListStatus.formatRating(requireContext())

        tvEpisodeProgress.text = getString(
            R.string.anime_detail_episodes_progress,
            anime.myListStatus.nbEpisodesWatched.toString(),
            anime.nbEpisodes.toString()
        )

        val alternativeTitles: AlternativeTitles = anime.alternativeTitles
        val enAndJaTitlesText: String = when {
            alternativeTitles.englishTitle.isNotBlank() && alternativeTitles.japaneseTitle.isNotBlank() ->
                "${alternativeTitles.englishTitle},\n${alternativeTitles.japaneseTitle}"
            alternativeTitles.englishTitle.isNotBlank() -> alternativeTitles.englishTitle
            alternativeTitles.japaneseTitle.isNotBlank() -> alternativeTitles.japaneseTitle
            else -> ""
        }
        val alternativeTitlesText: String = anime.alternativeTitles.synonyms.fold(enAndJaTitlesText) { acc, synonym ->
            if (acc.isNotBlank()) "$acc,\n$synonym" else synonym
        }
        tvAlternativeTitlesBody.text = alternativeTitlesText

        tvSynopsisBody.text = anime.synopsis
    }

    // endregion
}
package mg.watched.animes.animes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mg.watched.animes.R
import mg.watched.animes.databinding.AnimeItemBinding
import mg.watched.animes.utils.AnimeAnimations
import mg.watched.animes.utils.formatKindSeasonAiring
import mg.watched.data.anime.network.models.Anime

internal typealias AnimeSelectedCallback = (Anime, View) -> Unit

internal class AnimeAdapter(
    private val onAnimeSelectedCallback: AnimeSelectedCallback
) : PagedListAdapter<Anime, AnimeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder =
        AnimeViewHolder.newInstance(parent, onAnimeSelectedCallback)

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bindView(getItem(position)!!)
    }

    private class DiffCallback : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Anime, newItem: Anime) = oldItem == newItem
    }
}

internal class AnimeViewHolder private constructor(
    private val binding: AnimeItemBinding,
    private val animeSelectedCallback: AnimeSelectedCallback
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(parent: ViewGroup, animeSelectedCallback: AnimeSelectedCallback): AnimeViewHolder {
            val binding = AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AnimeViewHolder(binding, animeSelectedCallback)
        }
    }

    fun bindView(anime: Anime) {
        val context: Context = itemView.context

        itemView.transitionName = AnimeAnimations.getAnimeMasterDetailTransitionName(anime)
        itemView.setOnClickListener { animeSelectedCallback(anime, itemView) }

        Glide.with(binding.ivPicture.context)
            .load(anime.mainPicture.mediumUrl)
            .into(binding.ivPicture)

        binding.tvTitle.text = anime.title
        binding.tvKindSeasonAiring.text = anime.formatKindSeasonAiring(context)

        val isAnimeInWatchlist: Boolean = anime.myListStatus != null
        if (isAnimeInWatchlist) {
            binding.tvEpisodeProgress.text = context.getString(
                R.string.anime_episode_progress,
                anime.myListStatus?.nbEpisodesWatched!!.toString(),
                anime.nbEpisodes.toString()
            )

            binding.pbEpisodeProgress.isVisible = true
            val progressPercent: Int =
                ((anime.myListStatus?.nbEpisodesWatched!!.toFloat() / anime.nbEpisodes) * 100).toInt()
            binding.pbEpisodeProgress.progress = progressPercent
        } else {
            binding.tvEpisodeProgress.text = context.resources.getQuantityString(
                R.plurals.anime_nb_episodes,
                anime.nbEpisodes,
                anime.nbEpisodes
            )
            binding.pbEpisodeProgress.isInvisible = true
        }

        // TODO: Bind Add watched episode action
    }
}

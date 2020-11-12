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
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_anime_watching.*
import mg.watched.animes.R
import mg.watched.animes.utils.AnimeAnimations
import mg.watched.animes.utils.formatKindSeasonAiring
import mg.watched.data.anime.network.models.Anime

internal typealias AnimeSelectedCallback = (Anime, View) -> Unit

internal class AnimeAdapter(
    private val onAnimeSelectedCallback: AnimeSelectedCallback
) : PagedListAdapter<Anime, AnimeViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position)!!.id

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
    view: View,
    private val animeSelectedCallback: AnimeSelectedCallback
) : RecyclerView.ViewHolder(view), LayoutContainer {

    companion object {
        fun newInstance(parent: ViewGroup, animeSelectedCallback: AnimeSelectedCallback): AnimeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_anime_watching, parent, false)
            return AnimeViewHolder(view, animeSelectedCallback)
        }
    }

    override val containerView: View?
        get() = itemView

    fun bindView(anime: Anime) {
        val context: Context = itemView.context

        itemView.transitionName = AnimeAnimations.getAnimeMasterDetailTransitionName(anime)
        itemView.setOnClickListener { animeSelectedCallback(anime, itemView) }

        Glide.with(ivPicture.context)
            .load(anime.mainPicture.mediumUrl)
            .into(ivPicture)

        tvTitle.text = anime.title
        tvKindSeasonAiring.text = anime.formatKindSeasonAiring(context)

        val isAnimeInWatchlist: Boolean = anime.myListStatus != null
        if (isAnimeInWatchlist) {
            tvEpisodeProgress.text = context.getString(
                R.string.anime_episode_progress,
                anime.myListStatus?.nbEpisodesWatched!!.toString(),
                anime.nbEpisodes.toString()
            )

            pbEpisodeProgress.isVisible = true
            val progressPercent: Int =
                ((anime.myListStatus?.nbEpisodesWatched?.toFloat() ?: .0f / anime.nbEpisodes) * 100).toInt()
            pbEpisodeProgress.progress = progressPercent
        } else {
            tvEpisodeProgress.text = context.resources.getQuantityString(
                R.plurals.anime_nb_episodes,
                anime.nbEpisodes,
                anime.nbEpisodes
            )
            pbEpisodeProgress.isInvisible = true
        }

        // TODO: Bind Add watched episode action
    }
}

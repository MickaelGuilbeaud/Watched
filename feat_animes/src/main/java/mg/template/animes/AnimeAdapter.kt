package mg.template.animes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_anime_watching.*
import mg.template.data.anime.network.models.AiringStatus
import mg.template.data.anime.network.models.Anime

internal typealias AnimeSelectedCallback = (Anime) -> Unit

internal class AnimeAdapter(
    private val onAnimeSelectedCallback: AnimeSelectedCallback
) : ListAdapter<Anime, AnimeViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder =
        AnimeViewHolder.newInstance(parent, onAnimeSelectedCallback)

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bindView(getItem(position))
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

        itemView.setOnClickListener { animeSelectedCallback(anime) }

        Glide.with(ivPicture.context)
            .load(anime.mainPicture.mediumUrl)
            .into(ivPicture)

        tvTitle.text = anime.title

        val strSeason = context.getString(
            R.string.anime_season,
            anime.startSeason.season.capitalize(),
            anime.startSeason.year.toString()
        )
        tvKindSeasonAiring.text = when (anime.airingStatus) {
            AiringStatus.CURRENTLY_AIRING -> context.getString(
                R.string.anime_kind_season_airing,
                anime.mediaType.toString(),
                strSeason
            )
            else -> context.getString(
                R.string.anime_kind_season,
                anime.mediaType.toString(),
                strSeason
            )
        }

        tvEpisodeProgress.text = context.getString(
            R.string.anime_episode_progress,
            anime.myListStatus.nbEpisodesWatched.toString(),
            anime.nbEpisodes.toString()
        )
        val progressPercent: Int = ((anime.myListStatus.nbEpisodesWatched.toFloat() / anime.nbEpisodes) * 100).toInt()
        pbEpisodeProgress.progress = progressPercent

        // TODO: Bind Add watched episode action
    }
}
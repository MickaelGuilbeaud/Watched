package mg.template.animes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
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
            val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_anime, parent, false)
            return AnimeViewHolder(view, animeSelectedCallback)
        }
    }

    override val containerView: View?
        get() = itemView

    fun bindView(anime: Anime) {
        itemView.setOnClickListener { animeSelectedCallback(anime) }

        /*
        Glide.with(ivPicture.context)
            .load(anime.imageUrl)
            .into(ivPicture)

        tvTitle.text = anime.title

        tvProducerAndEpisodes.text = when {
            anime.producers.isNotEmpty() && anime.episodes != null -> itemView.context.getString(
                R.string.anime_producer_and_episodes,
                anime.producers.first(),
                anime.episodes.toString()
            )
            anime.producers.isNotEmpty() -> anime.producers.first()
            anime.episodes != null -> itemView.context.getString(R.string.anime_episodes, anime.episodes.toString())
            else -> ""
        }

        vgGenres.removeAllViews()
        val layoutInflater: LayoutInflater = LayoutInflater.from(itemView.context)
        anime.genres.forEach { genre ->
            val tvGenre: TextView =
                layoutInflater.inflate(R.layout.listitem_anime_genre, vgGenres, false) as TextView
            tvGenre.text = genre
            vgGenres.addView(tvGenre)
        }
         */
    }
}
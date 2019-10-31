package mg.template.featurea

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mg.template.data.pokemon.network.Pokemon

internal class PokemonAdapter : ListAdapter<Pokemon, PokemonViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder.newInstance(parent)

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindPokemon(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem == newItem
    }
}

internal class PokemonViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun newInstance(parent: ViewGroup): PokemonViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_pokemon, parent, false)
            return PokemonViewHolder(view)
        }
    }

    private val ivSprite: ImageView by lazy { itemView.findViewById<ImageView>(R.id.ivSprite) }
    private val tvName: TextView by lazy { itemView.findViewById<TextView>(R.id.tvName) }
    private val tvNumber: TextView by lazy { itemView.findViewById<TextView>(R.id.tvNumber) }

    fun bindPokemon(pokemon: Pokemon) {
        Glide.with(ivSprite.context)
            .load(pokemon.sprites.frontDefault)
            .into(ivSprite)

        tvName.text = pokemon.name.capitalize()
        tvNumber.text = tvNumber.context.getString(R.string.pokemon_listitem_number, pokemon.id.toString())
    }
}
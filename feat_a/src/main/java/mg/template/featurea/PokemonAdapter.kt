package mg.template.featurea

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mg.template.data.pokemon.network.Pokemon
import mg.template.design.getColor
import mg.template.design.getName

internal class PokemonAdapter : ListAdapter<Pokemon, PokemonViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).id

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
    private val tvType1: TextView by lazy { itemView.findViewById<TextView>(R.id.tvType1) }
    private val tvType2: TextView by lazy { itemView.findViewById<TextView>(R.id.tvType2) }
    private val tvNumber: TextView by lazy { itemView.findViewById<TextView>(R.id.tvNumber) }

    fun bindPokemon(pokemon: Pokemon) {
        Glide.with(ivSprite.context)
            .load(pokemon.sprites.frontDefault)
            .into(ivSprite)

        tvName.text = pokemon.name.capitalize()
        tvNumber.text = tvNumber.context.getString(R.string.pokemon_listitem_number, pokemon.id.toString())

        bindType1(pokemon)
        bindType2(pokemon)
    }

    private fun bindType1(pokemon: Pokemon) {
        tvType1.apply {
            text = context.getString(pokemon.types[0].getName())

            val type1Color = ContextCompat.getColor(context, pokemon.types[0].getColor())
            backgroundTintList = ColorStateList.valueOf(type1Color)
        }
    }

    private fun bindType2(pokemon: Pokemon) {
        tvType2.apply {
            if (pokemon.types.size == 2) {
                isVisible = true
                text = context.getString(pokemon.types[1].getName())

                val type2Color = ContextCompat.getColor(context, pokemon.types[1].getColor())
                backgroundTintList = ColorStateList.valueOf(type2Color)
            } else {
                isVisible = false
            }
        }
    }
}
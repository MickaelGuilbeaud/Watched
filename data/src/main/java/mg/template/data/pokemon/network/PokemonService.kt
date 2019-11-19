package mg.template.data.pokemon.network

import io.reactivex.Maybe
import mg.template.data.pokemon.network.models.PokemonEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{pokemon_id}")
    fun getPokemonDetails(
        @Path("pokemon_id") pokemonId: Long
    ): Maybe<PokemonEntity>
}
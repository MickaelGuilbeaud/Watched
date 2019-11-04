package mg.template.data.pokemon.network

import io.reactivex.Single
import mg.template.data.pokemon.network.models.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{pokemon_id}")
    fun getPokemonDetails(
        @Path("pokemon_id") pokemonId: Long
    ): Single<Pokemon>
}
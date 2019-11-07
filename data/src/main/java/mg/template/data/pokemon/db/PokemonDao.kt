package mg.template.data.pokemon.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import mg.template.data.pokemon.db.models.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon WHERE id BETWEEN :start AND :endInclusive")
    fun getPokemonsInRange(start: Int, endInclusive: Int): Single<List<Pokemon>>

    @Query("DELETE FROM pokemon WHERE id BETWEEN :start AND :endInclusive")
    fun deletePokemonsInRange(start: Int, endInclusive: Int)

    @Insert
    fun insertAll(pokemons: List<Pokemon>)
}
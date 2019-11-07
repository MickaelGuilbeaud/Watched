package mg.template.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mg.template.data.pokemon.db.PokemonDao
import mg.template.data.pokemon.db.models.Pokemon
import mg.template.data.pokemon.db.models.RoomTypeConverters

@Database(entities = [Pokemon::class], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class TemplateDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}
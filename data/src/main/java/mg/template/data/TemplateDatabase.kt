package mg.template.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mg.template.core.RoomConverters
import mg.template.data.anime.db.AnimeDao
import mg.template.data.anime.db.models.Anime

@Database(entities = [Anime::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class TemplateDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
}
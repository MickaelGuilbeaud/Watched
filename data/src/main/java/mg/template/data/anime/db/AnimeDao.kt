package mg.template.data.anime.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable
import mg.template.data.anime.db.models.Anime

@Dao
interface AnimeDao {

    @Query("DELETE FROM anime")
    fun deleteAll()

    @Insert
    fun insert(animes: List<Anime>)

    @Query("SELECT * FROM anime ORDER BY anime.sortOrder")
    fun getAllStream(): Observable<List<Anime>>
}
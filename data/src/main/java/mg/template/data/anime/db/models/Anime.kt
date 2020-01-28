package mg.template.data.anime.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Anime(
    @PrimaryKey val malId: Long,
    val title: String,
    val imageUrl: String,
    val synopsis: String?,
    val episodes: Int?,
    val genres: List<String> = emptyList(),
    val producers: List<String> = emptyList(),
    val score: Double?
)
package mg.watched.core

import androidx.room.TypeConverter

class RoomConverters {

    @TypeConverter
    fun fromListOfString(value: List<String>): String = value.joinToString(";")

    @TypeConverter
    fun toListOfString(value: String): List<String> = value.split(";")
}
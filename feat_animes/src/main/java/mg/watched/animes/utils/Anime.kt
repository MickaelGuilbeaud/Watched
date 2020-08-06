package mg.watched.animes.utils

import android.content.Context
import mg.watched.animes.R
import mg.watched.data.anime.network.models.AiringStatus
import mg.watched.data.anime.network.models.Anime
import mg.watched.data.anime.network.models.MyListStatus
import java.security.InvalidParameterException

fun Anime.formatKindSeasonAiring(context: Context): String {
    val strSeason = context.getString(
        R.string.anime_season,
        startSeason.season.capitalize(),
        startSeason.year.toString()
    )
    return when (airingStatus) {
        AiringStatus.CURRENTLY_AIRING -> context.getString(
            R.string.anime_kind_season_airing,
            mediaType.toString(),
            strSeason
        )
        else -> context.getString(R.string.anime_kind_season, mediaType.toString(), strSeason)
    }
}

fun MyListStatus.formatRating(context: Context): String {
    return when (score.toInt()) {
        1 -> context.getString(R.string.anime_rating_one)
        2 -> context.getString(R.string.anime_rating_two)
        3 -> context.getString(R.string.anime_rating_three)
        4 -> context.getString(R.string.anime_rating_four)
        5 -> context.getString(R.string.anime_rating_five)
        6 -> context.getString(R.string.anime_rating_six)
        7 -> context.getString(R.string.anime_rating_seven)
        8 -> context.getString(R.string.anime_rating_eight)
        9 -> context.getString(R.string.anime_rating_nine)
        10 -> context.getString(R.string.anime_rating_ten)
        else -> throw InvalidParameterException("Status score should be comprised between 1 and 10")
    }
}
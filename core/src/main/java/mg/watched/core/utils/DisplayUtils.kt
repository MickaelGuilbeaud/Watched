package mg.watched.core.utils

import android.content.Context

fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density + .5f).toInt()

fun Float.toPx(context: Context): Float = this * context.resources.displayMetrics.density

fun Int.toDp(context: Context): Int = (this / context.resources.displayMetrics.density + .5f).toInt()

fun Float.toDp(context: Context): Float = this / context.resources.displayMetrics.density
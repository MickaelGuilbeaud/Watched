package mg.watched.core.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.ColorInt

@ColorInt
fun Context.getWindowBackgroundColor(): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(android.R.attr.windowBackground, typedValue, true)
    return typedValue.data
}

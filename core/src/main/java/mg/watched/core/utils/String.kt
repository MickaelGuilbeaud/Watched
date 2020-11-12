package mg.watched.core.utils

fun String?.isAValidEmail(): Boolean {
    return !this.isNullOrBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

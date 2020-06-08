package mg.template.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import mg.template.data.user.User

class UltimateListPreferences(
    private val preferences: SharedPreferences,
    private val moshi: Moshi
) : SharedPreferences by preferences {

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val USER = "user"
    }

    var accessToken: String?
        get() = getString(ACCESS_TOKEN, null)
        set(value) {
            edit { putString(ACCESS_TOKEN, value) }
        }

    var user: User?
        get() {
            val userJson: String? = getString(USER, null)
            return if (userJson != null) moshi.adapter(User::class.java).fromJson(userJson) else null
        }
        set(value) {
            val userJson: String? = if (value != null) moshi.adapter(User::class.java).toJson(user) else null
            edit { putString(USER, userJson) }
        }
}
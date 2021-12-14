import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class MojeNastavitve(val context: Context) {

    companion  object {
        private const val DARK_STATUS = "io.github.manuelernesto.DARK_STATUS"
    }

    private val nastavitve = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = nastavitve.getInt(DARK_STATUS, 0)
        set(value) = nastavitve.edit().putInt(DARK_STATUS, value).apply()
}
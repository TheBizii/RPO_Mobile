import android.app.Application
import java.util.*
import kotlin.collections.ArrayList

public class Kosarica : Application() {
    companion object {
        @JvmField
        var arrayList: ArrayList<String> = ArrayList()
        var duplicates: HashMap<String, Int> = HashMap()
        var prices: HashMap<String, Double> = HashMap()
        var skupaj: Double = 0.00
    }
}
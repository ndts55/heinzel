package ndts.heinzelnisseandroid.json

import org.json.JSONArray
import org.json.JSONException

class SafeJSONArray(content: String) : JSONArray(content) {

    override fun getJSONObject(index: Int): SafeJSONObject? = try {
        SafeJSONObject(super.getJSONObject(index).toString())
    } catch (je: JSONException) {
        null
    }


    operator fun iterator(): Iterator<SafeJSONObject> =
            (0 until length())
                    .asSequence()
                    .map { SafeJSONObject(get(it).toString()) }
                    .iterator()
}
package ndts.heinzelnisseandroid.json

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SafeJSONArray(content: String) : JSONArray(content) {

    override fun getJSONObject(index: Int): SafeJSONObject? {
        return try {
            SafeJSONObject(super.getJSONObject(index).toString())
        } catch (je: JSONException) {
            null
        }
    }

    operator fun iterator(): Iterator<JSONObject> = (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()
}
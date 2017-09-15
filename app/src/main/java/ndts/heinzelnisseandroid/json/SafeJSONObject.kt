package ndts.heinzelnisseandroid.json

import org.json.JSONException
import org.json.JSONObject

class SafeJSONObject(content: String) : JSONObject(content) {

    override fun getString(key: String?): String = try {
        super.getString(key)
    } catch (je: JSONException) {
        ""
    }

    override fun getInt(key: String?): Int = try {
        super.getInt(key)
    } catch (je: JSONException) {
        0
    }

    override fun getJSONObject(key: String?): JSONObject? = try {
        super.getJSONObject(key)
    } catch (je: JSONException) {
        null
    }

    override fun getJSONArray(key: String?): SafeJSONArray? = try {
        SafeJSONArray(super.getJSONArray(key).toString())
    } catch (je: JSONException) {
        null
    }

    fun getStringList(name: String): List<String> = try {
        val arr = getJSONArray(name)
        val lst: ArrayList<String> = ArrayList()
        if (arr != null) {
            for (it: SafeJSONObject in arr) {
                lst.add(it.toString())
            }
        }
        lst
    } catch (je: JSONException) {
        ArrayList()
    }
}
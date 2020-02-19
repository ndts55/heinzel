package ndts.heinzelnisseandroid.json

import org.json.JSONException
import org.json.JSONObject

class SafeJSONObject(content: String) : JSONObject(content) {

    override fun getString(key: String?): String =
            if (key == null) {
                ""
            } else {
                super.getString(key)
            }

    override fun getInt(key: String?): Int =
            if (key == null) {
                0
            } else {
                super.getInt(key)
            }

    override fun getJSONObject(key: String?): JSONObject? =
            if (key == null) {
                null
            } else {
                super.getJSONObject(key)
            }

    override fun getJSONArray(key: String?): SafeJSONArray? =
            if (key == null) {
                null
            } else {
                SafeJSONArray(super.getJSONArray(key).toString())
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
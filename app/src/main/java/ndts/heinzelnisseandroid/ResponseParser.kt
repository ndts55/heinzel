package ndts.heinzelnisseandroid

import android.content.Context
import ndts.heinzelnisseandroid.json.SafeJSONObject
import ndts.heinzelnisseandroid.objectmodel.Response
import ndts.heinzelnisseandroid.objectmodel.TranslationEntry
import ndts.heinzelnisseandroid.objectmodel.Word

class ResponseParser(private val context: Context) {
    fun parse(stringJSON: String): Response {
        val json = SafeJSONObject(stringJSON)

        val translations = parseTranslations(json)
        val phonetics = parsePhonetics(json)
        val words = parseWords(json)
        val searchItem = parseSearchItem(json)

        return Response(
                searchItem,
                phonetics.first, // german
                phonetics.second, // norwegian
                words.first, // bokmål
                words.second, // nynorsk
                translations.first, // german
                translations.second // norwegian
        )
    }

    private fun parseTranslations(json: SafeJSONObject): Pair<List<TranslationEntry>, List<TranslationEntry>> {
        val deJson = json.getJSONArray(context.getString(R.string.deutsch_translations))
        val deList = ArrayList<TranslationEntry>()
        if (deJson != null) {
            for (elm: SafeJSONObject in deJson) {
                deList.add(parseEntry(elm))
            }
        }

        val noJson = json.getJSONArray(context.getString(R.string.norsk_translations))
        val noList = ArrayList<TranslationEntry>()
        if (noJson != null) {
            for (elm: SafeJSONObject in noJson) {
                noList.add(parseEntry(elm))
            }
        }

        return Pair(deList, noList)
    }

    private fun parseEntry(json: SafeJSONObject): TranslationEntry = TranslationEntry(
            json.getString("bokmaalLink"),
            json.getString("grade"),
            json.getInt("id"),
            Word(
                    json.getString("article"),
                    json.getString("word"),
                    json.getString("other")
            ),
            Word(
                    json.getString("t_article"),
                    json.getString("t_word"),
                    json.getString("t_other")
            )
    )

    private fun parsePhonetics(json: SafeJSONObject): Pair<List<String>, List<String>> = Pair(
            json.getStringList(context.getString(R.string.deutsch_phonetics)),
            json.getStringList(context.getString(R.string.norsk_phonetics))
    )

    private fun parseWords(json: SafeJSONObject): Pair<List<String>, List<String>> = Pair(
            json.getStringList(context.getString(R.string.bokmaal_words)),
            json.getStringList(context.getString(R.string.nynorsk_words))
    )

    private fun parseSearchItem(json: SafeJSONObject): String = json.getString(context.getString(R.string.search_item))
}
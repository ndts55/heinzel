package ndts.heinzelnisseandroid

import ndts.heinzelnisseandroid.json.SafeJSONObject
import ndts.heinzelnisseandroid.objectmodel.Response
import ndts.heinzelnisseandroid.objectmodel.TranslationEntry
import ndts.heinzelnisseandroid.objectmodel.Word

typealias TranslationListPair = Pair<List<TranslationEntry>, List<TranslationEntry>>

class ResponseParser {
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

    private fun parseTranslations(json: SafeJSONObject): TranslationListPair =
            Pair(
                    parseSingleTranslation(json, "deTrans"),
                    parseSingleTranslation(json, "noTrans")
            )

    private fun parseSingleTranslation(json: SafeJSONObject, name: String): List<TranslationEntry> {
        val array = json.getJSONArray(name)
        val ret = ArrayList<TranslationEntry>()

        if (array != null) {
            for (entry: SafeJSONObject in array) {
                ret.add(parseEntry(entry))
            }
        }

        return ret
    }

    private fun parseEntry(json: SafeJSONObject): TranslationEntry =
            TranslationEntry(
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
            json.getStringList("dePhonetics"),
            json.getStringList("noPhonetics")
    )

    private fun parseWords(json: SafeJSONObject): Pair<List<String>, List<String>> = Pair(
            json.getStringList("bookmaalWords"),
            json.getStringList("nynorskWords")
    )

    private fun parseSearchItem(json: SafeJSONObject): String = json.getString("searchItem")
}
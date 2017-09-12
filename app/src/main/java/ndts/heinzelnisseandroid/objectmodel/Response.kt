package ndts.heinzelnisseandroid.objectmodel

class Response(private val searchItem: String,
               private val germanPhonetics: List<String>?,
               private val norwegianPhonetics: List<String>?,
               private val nynorskWords: List<String>?,
               private val bokmaalWords: List<String>?,
               private val germanTranslations: List<TranslationEntry>?,
               private val norwegianTranslations: List<TranslationEntry>?) {
    override fun toString(): String {
        val bob = StringBuilder()
        bob.append(searchItem)
        bob.append("\n")
        germanPhonetics?.forEach { bob.append(it + "\n") }
        bob.append("\n")
        norwegianPhonetics?.forEach { bob.append(it + "\n") }
        bob.append("\n")
        nynorskWords?.forEach { bob.append(it + "\n") }
        bob.append("\n")
        bokmaalWords?.forEach { bob.append(it + "\n") }
        bob.append("\n")
        germanTranslations?.forEach { bob.append(it.toString() + "\n") }
        bob.append("\n")
        norwegianTranslations?.forEach { bob.append(it.toString() + "\n") }
        return bob.toString()
    }
}
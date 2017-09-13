package ndts.heinzelnisseandroid.objectmodel

class Word(private val article: String,
           private val value: String,
           private val other: String) {

    override fun toString(): String {
        val bob = StringBuilder()
        bob.append(value)
        if (article.isNotEmpty()) bob.append(" ($article)")
        if (other.isNotEmpty()) bob.append("\n$other")
        return bob.toString()
    }
}
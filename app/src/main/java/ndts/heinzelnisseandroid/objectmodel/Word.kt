package ndts.heinzelnisseandroid.objectmodel

class Word(private val article: String,
           private val value: String,
           private val other: String) {

    public override fun toString(): String = String.format(
            "\tArticle:\t%s\n\tValue:\t%s\n\tOther:\t%s",
            article, value, other)
}
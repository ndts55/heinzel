package ndts.heinzelnisseandroid.objectmodel

class TranslationEntry(private val link: String,
                       private val grade: String,
                       private val id: Int,
                       private val original: Word,
                       private val translation: Word) {

    public override fun toString(): String = String.format(
            "Link:\t%s\nGrade:\t%s\nId:\t%d\nOriginal:\n%s\nTranslation:\n%s\n",
            link, grade, id, original.toString(), translation.toString())
}
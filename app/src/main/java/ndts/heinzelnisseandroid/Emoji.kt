package ndts.heinzelnisseandroid

object Emoji {
    public fun arrow(): String = getByUnicode(0x2192)

    public fun norwegianFlag(): String = getByUnicode(0x1f1f3) + getByUnicode(0x1f1f4)

    public fun germanFlag(): String = getByUnicode(0x1f1e9) + getByUnicode(0x1f1ea)

    private fun getByUnicode(unicode: Int): String = String(Character.toChars(unicode))
}
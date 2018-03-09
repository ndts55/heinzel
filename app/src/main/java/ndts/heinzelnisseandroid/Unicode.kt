package ndts.heinzelnisseandroid

object Unicode {
    fun arrow(): String = convert(0x2192)

    fun norwegianFlag(): String = convert(0x1f1f3) + convert(0x1f1f4)

    fun germanFlag(): String = convert(0x1f1e9) + convert(0x1f1ea)

    private fun convert(unicode: Int): String = String(Character.toChars(unicode))
}
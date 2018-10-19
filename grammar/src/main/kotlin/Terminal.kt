package grammar

data class Terminal constructor(
        val literal: String
) {

    companion object {
        fun endOfInput(): Terminal =
                Terminal(Grammar.END_OF_INPUT_SYMBOL).apply { type = SymbolType.UNDEFINED }

        fun emptySymbol(): Terminal =
                Terminal(Grammar.EMPTY_SYMBOL).apply { type = SymbolType.UNDEFINED }
    }

    var type: SymbolType = SymbolType.UNDEFINED
    val isEpsilon: Boolean = literal == Grammar.EMPTY_SYMBOL
    val isDollar: Boolean = literal == Grammar.END_OF_INPUT_SYMBOL

    fun toSymbol(): GrammarSymbol = GrammarSymbol.from(this)

    override fun toString(): String = literal

}

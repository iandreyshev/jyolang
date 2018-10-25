package grammar

data class Terminal constructor(
        val literal: String
) {

    constructor(literal: String, type: SymbolType) : this(literal) {
        this.type = type
    }

    companion object {
        fun endOfInput(): Terminal =
                Terminal(Grammar.END_OF_INPUT_SYMBOL, SymbolType.END_OF_INPUT)

        fun emptySymbol(): Terminal =
                Terminal(Grammar.EMPTY_SYMBOL, SymbolType.UNDEFINED)
    }

    var type: SymbolType = SymbolType.UNDEFINED
        private set
    val isEpsilon: Boolean = literal == Grammar.EMPTY_SYMBOL
    val isDollar: Boolean = literal == Grammar.END_OF_INPUT_SYMBOL

    fun toSymbol(): GrammarSymbol = GrammarSymbol.from(this)

    override fun toString(): String = literal

}

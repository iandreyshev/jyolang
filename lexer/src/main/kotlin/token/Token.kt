package token

import grammar.SymbolType
import grammar.Terminal

data class Token(
        val terminal: Terminal,
        val position: Int = DEFAULT_POSITION,
        val rowNumber: Int = DEFAULT_ROW_NUMBER
) {

    constructor(literal: String, type: SymbolType, position: Int = DEFAULT_POSITION, rowNumber: Int = DEFAULT_ROW_NUMBER)
            : this(Terminal(literal).apply { this.type = type }, position, rowNumber)

    companion object {
        private const val DEFAULT_POSITION = 0
        private const val DEFAULT_ROW_NUMBER = 0

        fun eof() = Token(Terminal("").apply { type = SymbolType.END_OF_FILE })
    }

    val type: SymbolType
        get() = terminal.type

    val isEof: Boolean
        get() = type == SymbolType.END_OF_FILE

    val isUndefined: Boolean
        get() = type == SymbolType.UNDEFINED

    val isEofOrUndefined: Boolean
        get() = isEof || isUndefined

    override fun toString(): String {
        return "[literal: '${terminal.literal}', " +
                "type: $type, " +
                "position: $position, " +
                "rowNumber: $rowNumber]"
    }

}

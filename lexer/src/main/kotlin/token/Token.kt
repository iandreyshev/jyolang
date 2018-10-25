package token

import grammar.SymbolType
import grammar.Terminal

data class Token(
        val terminal: Terminal,
        val position: Int = DEFAULT_POSITION,
        val rowNumber: Int = DEFAULT_ROW_NUMBER
) {

    constructor(literal: String, type: SymbolType, position: Int = DEFAULT_POSITION, rowNumber: Int = DEFAULT_ROW_NUMBER)
            : this(Terminal(literal, type), position, rowNumber)

    companion object {
        private const val DEFAULT_POSITION = 0
        private const val DEFAULT_ROW_NUMBER = 0

        fun eof(position: Int = DEFAULT_POSITION, rowNumber: Int = DEFAULT_ROW_NUMBER) =
                Token(Terminal.endOfInput(), position, rowNumber)

        fun undefined(literal: String = "", position: Int = DEFAULT_POSITION, rowNumber: Int = DEFAULT_ROW_NUMBER) =
                Token(literal, SymbolType.UNDEFINED, position, rowNumber)
    }

    val type: SymbolType
        get() = terminal.type

    val isEof: Boolean
        get() = type == SymbolType.END_OF_INPUT

    val isUndefined: Boolean
        get() = type == SymbolType.UNDEFINED

    override fun toString(): String {
        return "[literal: '${terminal.literal}', " +
                "type: $type, " +
                "position: $position, " +
                "rowNumber: $rowNumber]"
    }

}

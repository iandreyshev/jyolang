package parser.comparator

import grammar.SymbolType
import grammar.Terminal
import token.Token

object Comparator : IComparator {

    override fun compare(terminal: Terminal, token: Token): Boolean = when (terminal.type) {
        SymbolType.IDENTIFIER,
        SymbolType.NUMBER,
        SymbolType.KEYWORD_FUNCTION,
        SymbolType.KEYWORD_IF,
        SymbolType.KEYWORD_ELSE,
        SymbolType.KEYWORD_WHILE,
        SymbolType.KEYWORD_VAR,
        SymbolType.KEYWORD_RETURN,
        SymbolType.TYPE_INT,
        SymbolType.TYPE_FLOAT,
        SymbolType.TYPE_BOOLEAN,
        SymbolType.TYPE_ARRAY,
        SymbolType.BOOLEAN_TRUE,
        SymbolType.BOOLEAN_FALSE,
        SymbolType.UNDEFINED -> {
            terminal.type == token.type
        }
        SymbolType.SYMBOL_OPERATOR -> {
            terminal.literal == token.terminal.literal
        }
        SymbolType.END_OF_INPUT -> {
            terminal.isDollar
        }
    }

}
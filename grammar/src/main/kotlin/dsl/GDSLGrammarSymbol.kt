package grammar.dsl

import grammar.SymbolType

data class GDSLGrammarSymbol(
        val symbol: String,
        val type: SymbolType? = null
)

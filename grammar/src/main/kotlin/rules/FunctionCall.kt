package grammar.rules

import dsl.grammarRules
import grammar.SymbolType

val FUNCTION_CALL = grammarRules {
    nonTerminal(RuleName.FUNCTION_CALL) {
        reproducedSymbolsSequence(rule("Identifier"), work("("), rule("ParamList") ,work(")"))
    }
    nonTerminal("ParamList") {
        reproducedRulesSequence("Param", "TailParamList")
        reproducedEmptySymbol()
    }
    nonTerminal("TailParamList") {
        reproducedSymbolsSequence(work(","), rule("Param"), rule("TailParamList"))
        reproducedEmptySymbol()
    }
    nonTerminal("Param") {
        reproducedSymbolsSequence(RuleName.IDENTIFIER with SymbolType.IDENTIFIER)
    }
}

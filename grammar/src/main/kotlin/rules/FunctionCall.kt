package grammar.rules

import dsl.grammarRules
import grammar.SymbolType

val FUNCTION_CALL = grammarRules {
    nonTerminal(RuleName.FUNCTION_CALL) {
        reproducedSymbolsSequence(
                Keyword.Yo with SymbolType.KEYWORD_YO, operator("."), identifier(), operator("("),
                rule("ParamList") ,operator(")"))
    }
    nonTerminal("ParamList") {
        reproducedRulesSequence(RuleName.VAR_READ, "TailParamList")
        reproducedEmptySymbol()
    }
    nonTerminal("TailParamList") {
        reproducedSymbolsSequence(operator(","), rule(RuleName.VAR_READ), rule("TailParamList"))
        reproducedEmptySymbol()
    }
}

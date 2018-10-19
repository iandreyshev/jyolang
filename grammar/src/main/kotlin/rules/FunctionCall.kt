package grammar.rules

import dsl.grammarRules

const val FUNCTION_CALL_RULES_ROOT = "FunctionCall"

val FUNCTION_CALL = grammarRules {
    nonTerminal(FUNCTION_CALL_RULES_ROOT) {
        reproduced("Identifier", "(", "ParamList" ,")")
    }
    nonTerminal("ParamList") {
        reproduced("Param", "TailParamList")
        reproducedEmptySymbol()
    }
    nonTerminal("TailParamList") {
        reproduced(",", "Param", "TailParamList")
        reproducedEmptySymbol()
    }
    nonTerminal("Param") {
        reproduced(RuleName.IDENTIFIER)
    }
}

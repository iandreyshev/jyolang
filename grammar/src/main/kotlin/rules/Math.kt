package grammar.rules

import dsl.grammarRules
import grammar.SymbolType

val EXPRESSION = grammarRules {
    nonTerminal(RuleName.EXPRESSION) {
        reproducedRulesSequence(EXPR)
    }
    nonTerminal(EXPR) {
        reproducedRulesSequence(TERM, TAIL_EXPR)
    }
    nonTerminal(TAIL_EXPR) {
        reproducedSymbolsSequence(
                operator(Operator.Plus), rule(TERM), rule(TAIL_EXPR))
        reproducedSymbolsSequence(
                operator(Operator.Minus), rule(TERM), rule(TAIL_EXPR))
        reproducedSymbolsSequence(
                operator(Operator.Or), rule(ATOM), rule(TAIL_TERM))
        reproducedEmptySymbol()
    }
    nonTerminal(TERM) {
        reproducedRulesSequence(ATOM, TAIL_TERM)
    }
    nonTerminal(TAIL_TERM) {
        reproducedSymbolsSequence(
                operator(Operator.Mul), rule(ATOM), rule(TAIL_TERM))
        reproducedSymbolsSequence(
                operator(Operator.Div), rule(ATOM), rule(TAIL_TERM))
        reproducedSymbolsSequence(
                operator(Operator.And), rule(ATOM), rule(TAIL_TERM))
        reproducedSymbolsSequence(
                operator(Operator.Less), rule(ATOM), rule(TAIL_TERM))
        reproducedSymbolsSequence(
                operator(Operator.More), rule(ATOM), rule(TAIL_TERM))
        reproducedSymbolsSequence(
                operator(Operator.Equal), rule(ATOM), rule(TAIL_TERM))
        reproducedEmptySymbol()
    }
    nonTerminal(ATOM) {
        reproducedSymbol(number())
        reproducedSymbolsSequence(identifier(), rule(ATOM_PARAMETER))
        reproducedSymbolsSequence(operator("("), rule(EXPR), operator(")"))
        reproducedSymbolsSequence(operator(Operator.Not), rule(ATOM))
        reproducedSymbolsSequence(operator(Operator.Minus), rule(ATOM))
        reproducedSymbol(Keyword.BooleanTrue, SymbolType.BOOLEAN_TRUE)
        reproducedSymbol(Keyword.BooleanFalse, SymbolType.BOOLEAN_FALSE)
    }
    nonTerminal(ATOM_PARAMETER) {
        reproducedSymbolsSequence(operator(Operator.NotEqual), rule(ATOM))
        reproducedSymbolsSequence(operator("("), rule("ParamList") ,operator(")"))
        reproducedRulesSequence(RuleName.GET_BY_INDEX)
        reproducedEmptySymbol()
    }
    nonTerminal("ParamList") {
        reproducedSymbolsSequence(rule(EXPR), rule("TailParamList"))
        reproducedEmptySymbol()
    }
    nonTerminal("TailParamList") {
        reproducedSymbolsSequence(
                operator(","), rule(EXPR),rule("TailParamList"))
        reproducedEmptySymbol()
    }
    nonTerminal(RuleName.GET_BY_INDEX) {
        reproducedSymbolsSequence(operator("["), rule(ATOM), operator("]"))
    }
}

private const val EXPR = RuleName.EXPRESSION + "Expr"
private const val TERM = RuleName.EXPRESSION + "Term"
private const val TAIL_EXPR = RuleName.EXPRESSION + "TailExpr"
private const val TAIL_TERM = RuleName.EXPRESSION + "TailTerm"
private const val ATOM = RuleName.EXPRESSION + "Factor"
private const val ATOM_PARAMETER = RuleName.EXPRESSION + "AtomParameter"

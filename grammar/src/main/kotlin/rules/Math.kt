package grammar.rules

import dsl.grammarRules
import grammar.SymbolType

val MATH_EXPRESSION = grammarRules {
    nonTerminal(RuleName.MATH_EXPRESSION) {
        reproducedRulesSequence(EXPR)
    }
    nonTerminal(EXPR) {
        reproducedRulesSequence(TERM, TAIL_EXPR)
    }
    nonTerminal(TAIL_EXPR) {
        reproducedSymbolsSequence(operator(Operator.Plus), rule(TERM), rule(TAIL_EXPR))
        reproducedSymbolsSequence(operator(Operator.Minus), rule(TERM), rule(TAIL_EXPR))
        reproducedEmptySymbol()
    }
    nonTerminal(TERM) {
        reproducedRulesSequence(FACTOR, TAIL_TERM)
    }
    nonTerminal(TAIL_TERM) {
        reproducedSymbolsSequence(operator(Operator.Mul), rule(FACTOR), rule(TAIL_TERM))
        reproducedSymbolsSequence(operator(Operator.Div), rule(FACTOR), rule(TAIL_TERM))
        reproducedEmptySymbol()
    }
    nonTerminal(FACTOR) {
        reproducedSymbolsSequence(operator("("), rule(EXPR), operator(")"))
        reproducedSymbolsSequence(operator(Operator.Minus), rule(FACTOR))
        reproducedSymbolsSequence(rule(RuleName.VAR_READ))
        reproducedSymbol(number())
    }
}

private const val EXPR = RuleName.MATH_EXPRESSION + "Expr"
private const val TERM = RuleName.MATH_EXPRESSION + "Term"
private const val TAIL_EXPR = RuleName.MATH_EXPRESSION + "TailExpr"
private const val TAIL_TERM = RuleName.MATH_EXPRESSION + "TailTerm"
private const val FACTOR = RuleName.MATH_EXPRESSION + "Factor"

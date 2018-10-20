package grammar.rules

import dsl.grammarRules
import grammar.SymbolType

val MATH_EXPRESSION = grammarRules {
    nonTerminal(RuleName.MATH_EXPRESSION) {
        reproducedRulesSequence("Expr")
    }
    nonTerminal("Expr") {
        reproducedRulesSequence("Term", "TailExpr")
    }
    nonTerminal("TailExpr") {
        reproducedSymbolsSequence(operator(Operator.Plus), rule("Term"), rule("TailExpr"))
        reproducedSymbolsSequence(operator(Operator.Minus), rule("Term"), rule("TailExpr"))
        reproducedEmptySymbol()
    }
    nonTerminal("Term") {
        reproducedRulesSequence("Factor", "TailTerm")
    }
    nonTerminal("TailTerm") {
        reproducedSymbolsSequence(operator(Operator.Mul), rule("Factor"), rule("TailTerm"))
        reproducedSymbolsSequence(operator(Operator.Div), rule("Factor"), rule("TailTerm"))
        reproducedEmptySymbol()
    }
    nonTerminal("Factor") {
        reproducedSymbolsSequence(work("("), rule("Expr"), work(")"))
        reproducedSymbolsSequence(operator(Operator.Minus), rule("Factor"))
        reproducedSymbolsSequence("IntegerLiteral" with SymbolType.NUMBER)
        reproducedSymbolsSequence("FloatLiteral" with SymbolType.NUMBER)
        reproducedSymbolsSequence(identifier())
    }
}

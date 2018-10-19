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
        reproducedSymbolsSequence(Operator.Plus with SymbolType.OPERATOR, rule("Term"), rule("TailExpr"))
        reproducedSymbolsSequence(Operator.Minus with SymbolType.OPERATOR, rule("Term"), rule("TailExpr"))
        reproducedEmptySymbol()
    }
    nonTerminal("Term") {
        reproducedRulesSequence("Factor", "TailTerm")
    }
    nonTerminal("TailTerm") {
        reproducedSymbolsSequence(Operator.Mul with SymbolType.OPERATOR, rule("Factor"), rule("TailTerm"))
        reproducedSymbolsSequence(Operator.Div with SymbolType.OPERATOR, rule("Factor"), rule("TailTerm"))
        reproducedEmptySymbol()
    }
    nonTerminal("Factor") {
        reproducedSymbolsSequence(work("("), rule("Expr"), work(")"))
        reproducedSymbolsSequence(Operator.Minus with SymbolType.OPERATOR, rule("Factor"))
        reproducedSymbolsSequence("IntegerLiteral" with SymbolType.NUMBER)
        reproducedSymbolsSequence("FloatLiteral" with SymbolType.NUMBER)
        reproducedSymbolsSequence(RuleName.IDENTIFIER with SymbolType.IDENTIFIER)
    }
}

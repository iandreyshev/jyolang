package grammar.rules

import dsl.grammarRules

val MATH_EXPRESSION = grammarRules {
    nonTerminal(RuleName.MATH_EXPRESSION) {
        reproduced("Expr")
    }
    nonTerminal("Expr") {
        reproduced("Term", "TailExpr")
    }
    nonTerminal("TailExpr") {
        reproduced("+", "Term", "TailExpr")
        reproduced("-", "Term", "TailExpr")
        reproducedEmptySymbol()
    }
    nonTerminal("Term") {
        reproduced("Factor", "TailTerm")
    }
    nonTerminal("TailTerm") {
        reproduced("*", "Factor", "TailTerm")
        reproduced("/", "Factor", "TailTerm")
        reproducedEmptySymbol()
    }
    nonTerminal("Factor") {
        reproduced("(", "Expr", ")")
        reproduced("-", "Factor")
        reproduced("IntegerLiteral")
        reproduced("FloatLiteral")
        reproduced(RuleName.IDENTIFIER)
    }
}

package grammar.rules

import dsl.*
import grammar.SymbolType

/**
 * yolang
 **/
val YOLANG = grammarOf {
    nonTerminal("Program") {
        reproducedRulesSequence("FunctionList")
    }
    nonTerminal("FunctionList") {
        reproducedRulesSequence("Function", "FunctionList")
        reproducedEmptySymbol()
    }
    nonTerminal("Function") {
        reproducedSymbolsSequence(
                Keyword.Function with SymbolType.KEYWORD_FUNCTION, RuleName.IDENTIFIER with SymbolType.IDENTIFIER,
                work("("), rule("ParamDeclList"), work(")"), work("->"), rule("Type"), work(":"), rule("Statement"))
    }
    nonTerminal("ParamDeclList") {
        reproducedRulesSequence("ParamDecl", "TailParamDeclList")
        reproducedEmptySymbol()
    }
    nonTerminal("TailParamDeclList") {
        reproducedSymbolsSequence(
                work(","),
                rule("ParamDecl"),
                rule("TailParamDeclList"))
        reproducedEmptySymbol()
    }
    nonTerminal("ParamDecl") {
        reproducedSymbolsSequence(RuleName.IDENTIFIER with SymbolType.IDENTIFIER, work(":"), rule("Type"))
    }
    nonTerminal("Type") {
        reproducedSymbol(Type.Int, SymbolType.TYPE_INT)
        reproducedSymbol(Type.Float, SymbolType.TYPE_FLOAT)
        reproducedSymbol(Type.Boolean, SymbolType.TYPE_BOOLEAN)
        reproducedSymbolsSequence(Type.Array with SymbolType.TYPE_ARRAY, work("<"), rule("Type"), work(">"))
    }
    nonTerminal("Statement") {
        reproducedRulesSequence("Condition")
        reproducedRulesSequence("Loop")
        reproducedRulesSequence("Decl")
        reproducedRulesSequence("Assign")
        reproducedRulesSequence("Return")
        reproducedRulesSequence("CompositeStatement")
    }
    nonTerminal("Condition") {
        reproducedSymbolsSequence(
                Keyword.Condition with SymbolType.KEYWORD_IF, work("("), rule(RuleName.EXPRESSION), work(")"),
                rule("Statement"), rule("OptionalElse"))
    }
    nonTerminal("OptionalElse") {
        reproducedSymbolsSequence(Keyword.ConditionElse with SymbolType.KEYWORD_ELSE, rule("Statement"))
        reproducedEmptySymbol()
    }
    nonTerminal("Loop") {
        reproducedSymbolsSequence(
                Keyword.Cycle with SymbolType.KEYWORD_WHILE, work("("), rule(RuleName.EXPRESSION), work(")"),
                rule("Statement"))
    }
    nonTerminal("Decl") {
        reproducedSymbolsSequence(
                Keyword.VariableDecl with SymbolType.KEYWORD_VAR, RuleName.IDENTIFIER with SymbolType.IDENTIFIER,
                work(":"), rule("Type"), work(";"))
    }
    nonTerminal("Assign") {
        reproducedSymbolsSequence(
                RuleName.IDENTIFIER with SymbolType.IDENTIFIER, Operator.Assign with SymbolType.OPERATOR,
                rule(RuleName.EXPRESSION), work(";"))
    }
    nonTerminal("Return") {
        reproducedSymbolsSequence(
                Keyword.Return with SymbolType.KEYWORD_RETURN, rule(RuleName.EXPRESSION), work(";"))
    }
    nonTerminal("CompositeStatement") {
        reproducedSymbolsSequence(
                work("{"), rule("StatementList"), work("}"))
    }
    nonTerminal("StatementList") {
        reproducedRulesSequence("Statement", "StatementList")
        reproducedEmptySymbol()
    }
    nonTerminal(RuleName.EXPRESSION) {
        reproducedSymbol(Keyword.BooleanTrue, SymbolType.BOOLEAN_TRUE)
        reproducedSymbol(Keyword.BooleanFalse, SymbolType.BOOLEAN_FALSE)
        reproducedRulesSequence(RuleName.IDENTIFIER)
        reproducedRulesSequence(RuleName.MATH_EXPRESSION)
        reproducedRulesSequence(RuleName.FUNCTION_CALL)
    }
    rules(MATH_EXPRESSION)
    rules(FUNCTION_CALL)
}

object RuleName {
    const val IDENTIFIER = "Identifier"
    const val EXPRESSION = "Expression"
    const val MATH_EXPRESSION = "MathExpression"
    const val FUNCTION_CALL = "FunctionCall"
}

object Keyword {
    const val Function = "func"
    const val Condition = "if"
    const val ConditionElse = "else"
    const val Cycle = "while"
    const val VariableDecl = "var"
    const val Return = "return"
    const val BooleanTrue = "true"
    const val BooleanFalse = "false"
}

object Type {
    const val Int = "Int"
    const val Float = "Float"
    const val Boolean = "Bool"
    const val Array = "Array"
}

object Operator {
    const val Plus = "+"
    const val Minus = "-"
    const val Mul = "*"
    const val Div = "/"
    const val Assign = "="
}

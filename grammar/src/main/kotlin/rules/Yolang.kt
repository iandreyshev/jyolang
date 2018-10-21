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
                Keyword.Function with SymbolType.KEYWORD_FUNCTION, identifier(), operator("("),
                rule("ParamDeclList"), operator(")"), operator("->"), rule("Type"), operator(":"),
                rule("Statement"))
    }
    nonTerminal("ParamDeclList") {
        reproducedRulesSequence("ParamDecl", "TailParamDeclList")
        reproducedEmptySymbol()
    }
    nonTerminal("TailParamDeclList") {
        reproducedSymbolsSequence(operator(","), rule("ParamDecl"), rule("TailParamDeclList"))
        reproducedEmptySymbol()
    }
    nonTerminal("ParamDecl") {
        reproducedSymbolsSequence(identifier(), operator(":"), rule("Type"))
    }
    nonTerminal("Type") {
        reproducedSymbol(Type.Int, SymbolType.TYPE_INT)
        reproducedSymbol(Type.Float, SymbolType.TYPE_FLOAT)
        reproducedSymbol(Type.Boolean, SymbolType.TYPE_BOOLEAN)
        reproducedSymbolsSequence(
                Type.Array with SymbolType.TYPE_ARRAY, operator("<"), rule("Type"), operator(">"))
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
                Keyword.Condition with SymbolType.KEYWORD_IF, operator("("),
                rule(RuleName.EXPRESSION), operator(")"), rule("Statement"), rule("OptionalElse"))
    }
    nonTerminal("OptionalElse") {
        reproducedSymbolsSequence(
                Keyword.ConditionElse with SymbolType.KEYWORD_ELSE, rule("CompositeStatement"))
        reproducedEmptySymbol()
    }
    nonTerminal("Loop") {
        reproducedSymbolsSequence(
                Keyword.Cycle with SymbolType.KEYWORD_WHILE, operator("("),
                rule(RuleName.EXPRESSION), operator(")"), rule("Statement"))
    }
    nonTerminal("Decl") {
        reproducedSymbolsSequence(
                Keyword.VariableDecl with SymbolType.KEYWORD_VAR, identifier(), operator(":"),
                rule("Type"), operator(";"))
    }
    nonTerminal("Assign") {
        reproducedSymbolsSequence(
                identifier(), operator(Operator.Assign), rule(RuleName.EXPRESSION), operator(";"))
    }
    nonTerminal("Return") {
        reproducedSymbolsSequence(
                Keyword.Return with SymbolType.KEYWORD_RETURN, rule(RuleName.EXPRESSION),
                operator(";"))
    }
    nonTerminal("CompositeStatement") {
        reproducedSymbolsSequence(
                operator("{"), rule("StatementList"), operator("}"))
    }
    nonTerminal("StatementList") {
        reproducedRulesSequence("Statement", "StatementList")
        reproducedEmptySymbol()
    }
    nonTerminal(RuleName.EXPRESSION) {
        reproducedRulesSequence(RuleName.MATH_EXPRESSION)
    }
    nonTerminal(RuleName.VAR_READ) {
        reproducedSymbol(identifier())
        reproducedRulesSequence(RuleName.FUNCTION_CALL)
        reproducedSymbol(Keyword.BooleanTrue, SymbolType.BOOLEAN_TRUE)
        reproducedSymbol(Keyword.BooleanFalse, SymbolType.BOOLEAN_FALSE)
    }
    rules(MATH_EXPRESSION)
    rules(FUNCTION_CALL)
}

object Literal {
    const val Identifier = "Identifier"
    const val Number = "Number"
}

object RuleName {
    const val EXPRESSION = "Expression"
    const val MATH_EXPRESSION = "MathExpression"
    const val FUNCTION_CALL = "FunctionCall"
    const val VAR_READ = "VariableRead"
}

object Keyword {
    const val Yo = "Yo"
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

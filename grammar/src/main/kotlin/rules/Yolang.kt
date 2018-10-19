package grammar.rules

import dsl.*

/**
 * yolang
 **/
val YOLANG = grammarOf {
    nonTerminal("Program") {
        reproduced("FunctionList", Keyword.EOF)
    }
    nonTerminal("FunctionList") {
        reproduced("Function", "FunctionList")
        reproducedEmptySymbol()
    }
    nonTerminal("Function") {
        reproduced(Keyword.Function, RuleName.IDENTIFIER, "(", "ParamDeclList", ")", "->", "Type", ":", "Statement")
    }
    nonTerminal("ParamDeclList") {
        reproduced("ParamDecl", "TailParamDeclList")
        reproducedEmptySymbol()
    }
    nonTerminal("TailParamDeclList") {
        reproduced(",", "ParamDecl", "TailParamDeclList")
        reproducedEmptySymbol()
    }
    nonTerminal("ParamDecl") {
        reproduced(RuleName.IDENTIFIER, ":", "Type")
    }
    nonTerminal("Type") {
        reproduced(TypeName.Int)
        reproduced(TypeName.Float)
        reproduced(TypeName.Boolean)
        reproduced(TypeName.Array, "<", "Type", ">")
    }
    nonTerminal("Statement") {
        reproduced("Condition")
        reproduced("Loop")
        reproduced("Decl")
        reproduced("Assign")
        reproduced("Return")
        reproduced("CompositeStatement")
    }
    nonTerminal("Condition") {
        reproduced(Keyword.Condition, "(", RuleName.EXPRESSION, ")", "Statement", "OptionalElse")
    }
    nonTerminal("OptionalElse") {
        reproduced(Keyword.ConditionElse, "Statement")
        reproducedEmptySymbol()
    }
    nonTerminal("Loop") {
        reproduced(Keyword.CycleWithPreCondition, "(", RuleName.EXPRESSION, ")", "Statement")
    }
    nonTerminal("Decl") {
        reproduced(Keyword.VariableDecl, RuleName.IDENTIFIER, ":", "Type", ";")
    }
    nonTerminal("Assign") {
        reproduced(RuleName.IDENTIFIER, "=", RuleName.EXPRESSION, ";")
    }
    nonTerminal("Return") {
        reproduced("return", RuleName.EXPRESSION, ";")
    }
    nonTerminal("CompositeStatement") {
        reproduced("{", "StatementList", "}")
    }
    nonTerminal("StatementList") {
        reproduced("Statement", "StatementList")
        reproducedEmptySymbol()
    }
    nonTerminal(RuleName.EXPRESSION) {
        reproduced(Keyword.BooleanTrue)
        reproduced(Keyword.BooleanFalse)
        reproduced(RuleName.IDENTIFIER)
        reproduced(RuleName.MATH_EXPRESSION)
        reproduced(FUNCTION_CALL_RULES_ROOT)
    }
    rules(MATH_EXPRESSION)
    rules(FUNCTION_CALL)
}

object Keyword {
    const val Function = "func"
    const val Condition = "if"
    const val ConditionElse = "else"
    const val CycleWithPreCondition = "while"
    const val VariableDecl = "var"
    const val BooleanTrue = "true"
    const val BooleanFalse = "false"
    const val EOF = "EOF"
}

object TypeName {
    const val Int = "Int"
    const val Float = "Float"
    const val Boolean = "Bool"
    const val Array = "Array"
}

object RuleName {
    const val IDENTIFIER = "Identifier"
    const val EXPRESSION = "Expression"
    const val MATH_EXPRESSION = "MathExpression"
}

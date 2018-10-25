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
        reproducedRulesSequence("Print")
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
                Keyword.VariableDecl with SymbolType.KEYWORD_VAR, identifier(),
                operator(":"), rule("Type"), rule("TailDecl"), operator(";"))
    }
    nonTerminal("TailDecl") {
        reproducedSymbolsSequence(operator(Operator.Assign), rule(RuleName.EXPRESSION))
        reproducedEmptySymbol()
    }
    nonTerminal("Assign") {
        reproducedSymbolsSequence(
                rule("AssignSubject"), operator(Operator.Assign), rule(RuleName.EXPRESSION), operator(";"))
    }
    nonTerminal("AssignSubject") {
        reproducedSymbolsSequence(identifier(), rule("TailAssignSubject"))
    }
    nonTerminal("TailAssignSubject") {
        reproducedRulesSequence(RuleName.GET_BY_INDEX)
        reproducedEmptySymbol()
    }
    nonTerminal("Print") {
        reproducedSymbolsSequence(
                operator(Operator.Print), rule(RuleName.EXPRESSION), operator(";"))
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
    rules(EXPRESSION)
}

object Literal {
    const val Identifier = "Identifier"
    const val Number = "Number"
}

object RuleName {
    const val EXPRESSION = "Expression"
    const val GET_BY_INDEX = "GetByIndex"
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
    const val Print = ">>"
    const val Or = "||"
    const val And = "&&"
    const val Not = "!"
    const val Less = "<"
    const val More = ">"
    const val Equal = "=="
    const val NotEqual = "!="
    const val Plus = "+"
    const val Minus = "-"
    const val Mul = "*"
    const val Div = "/"
    const val Assign = "="
}

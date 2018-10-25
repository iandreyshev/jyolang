import grammar.GrammarSymbol
import grammar.NonTerminal
import grammar.Terminal
import grammar.rules.YOLANG
import grammar.rules.Keyword
import grammar.rules.Literal
import grammar.rules.Operator
import org.junit.Assert
import org.junit.Test

class FollowSetTest {

    @Test
    fun program() = "Program" expected setOf(
            Terminal.endOfInput()
    )

    @Test
    fun functionList() = "FunctionList" expected setOf(
            Terminal.endOfInput()
    )

    @Test
    fun function() = "Function" expected setOf(
            Terminal(Keyword.Function), Terminal.endOfInput()
    )

    @Test
    fun paramDeclList() = "ParamDeclList" expected setOf(
            Terminal(")")
    )

    @Test
    fun tailParamDeclList() = "TailParamDeclList" expected setOf(
            Terminal(")")
    )

    @Test
    fun paramDecl() = "ParamDecl" expected setOf(
            Terminal(","), Terminal(")")
    )

    @Test
    fun type() = "Type" expected setOf(
            Terminal(","), Terminal(":"), Terminal(")"), Terminal(";"), Terminal(">")
    )

    @Test
    fun statement() = "Statement" expected setOf(
            Terminal(Keyword.Function), Terminal(Literal.Identifier), Terminal(Keyword.Condition),
            Terminal(Keyword.ConditionElse), Terminal(Keyword.Cycle),
            Terminal(Keyword.VariableDecl), Terminal(Keyword.Return), Terminal("{"), Terminal("}"),
            Terminal.endOfInput()
    )

    @Test
    fun condition() = "Condition" expected setOf(
            Terminal(Keyword.Function), Terminal(Literal.Identifier), Terminal(Keyword.Condition),
            Terminal(Keyword.ConditionElse), Terminal(Keyword.Cycle),
            Terminal(Keyword.VariableDecl), Terminal(Keyword.Return), Terminal("{"), Terminal("}"),
            Terminal.endOfInput()
    )

    @Test
    fun optionalElse() = "OptionalElse" expected setOf(
            Terminal(Keyword.Function), Terminal(Literal.Identifier), Terminal(Keyword.Condition),
            Terminal(Keyword.ConditionElse), Terminal(Keyword.Cycle),
            Terminal(Keyword.VariableDecl), Terminal(Keyword.Return), Terminal("{"), Terminal("}"),
            Terminal.endOfInput()
    )

    @Test
    fun loop() = "Loop" expected setOf(
            Terminal(Keyword.Function), Terminal(Literal.Identifier), Terminal(Keyword.Condition),
            Terminal(Keyword.ConditionElse), Terminal(Keyword.Cycle),
            Terminal(Keyword.VariableDecl), Terminal(Keyword.Return), Terminal("{"), Terminal("}"),
            Terminal.endOfInput()
    )

    @Test
    fun decl() = "Decl" expected setOf(
            Terminal(Keyword.Function), Terminal(Literal.Identifier), Terminal(Keyword.Condition),
            Terminal(Keyword.ConditionElse), Terminal(Keyword.Cycle),
            Terminal(Keyword.VariableDecl), Terminal(Keyword.Return), Terminal("{"), Terminal("}"),
            Terminal.endOfInput()
    )

    @Test
    fun assign() = "Assign" expected setOf(
            Terminal(Keyword.Function), Terminal(Literal.Identifier), Terminal(Keyword.Condition),
            Terminal(Keyword.ConditionElse), Terminal(Keyword.Cycle),
            Terminal(Keyword.VariableDecl), Terminal(Keyword.Return), Terminal("{"), Terminal("}"),
            Terminal.endOfInput()
    )

    @Test
    fun `return`() = "Return" expected setOf(
            Terminal(Keyword.Function), Terminal(Literal.Identifier), Terminal(Keyword.Condition),
            Terminal(Keyword.ConditionElse), Terminal(Keyword.Cycle),
            Terminal(Keyword.VariableDecl), Terminal(Keyword.Return), Terminal("{"), Terminal("}"),
            Terminal.endOfInput()
    )

    @Test
    fun compositeStatement() = "CompositeStatement" expected setOf(
            Terminal(Keyword.Function), Terminal(Literal.Identifier), Terminal(Keyword.Condition),
            Terminal(Keyword.ConditionElse), Terminal(Keyword.Cycle),
            Terminal(Keyword.VariableDecl), Terminal(Keyword.Return), Terminal("{"), Terminal("}"),
            Terminal.endOfInput()
    )

    @Test
    fun statementList() = "StatementList" expected setOf(
            Terminal("}")
    )

    @Test
    fun expression() = "Expression" expected setOf(
            Terminal(")"), Terminal(";")
    )

    @Test
    fun variableRead() = "VariableRead" expected setOf(
            Terminal(","), Terminal(")"), Terminal(Operator.Minus), Terminal(Operator.Plus),
            Terminal(Operator.Div), Terminal(Operator.Mul), Terminal(";")
    )

    @Test
    fun mathExpression() = "MathExpression" expected setOf(
            Terminal(")"), Terminal(";")
    )

    @Test
    fun mathExpressionExpr() = "MathExpression" expected setOf(
            Terminal(")"), Terminal(";")
    )

    @Test
    fun mathExpressionTailExpr() = "MathExpressionTailExpr" expected setOf(
            Terminal(")"), Terminal(";")
    )

    @Test
    fun mathExpressionTerm() = "MathExpressionTerm" expected setOf(
            Terminal(")"), Terminal(Operator.Minus), Terminal(Operator.Plus), Terminal(";")
    )

    @Test
    fun mathExpressionTailTerm() = "MathExpressionTailTerm" expected setOf(
            Terminal(")"), Terminal(Operator.Minus), Terminal(Operator.Plus), Terminal(";")
    )

    @Test
    fun mathExpressionFactor() = "MathExpressionFactor" expected setOf(
            Terminal(")"), Terminal(Operator.Minus), Terminal(Operator.Plus),
            Terminal(Operator.Div), Terminal(Operator.Mul), Terminal(";")
    )

    @Test
    fun functionCall() = "FunctionCall" expected setOf(
            Terminal(","), Terminal(")"), Terminal(Operator.Minus), Terminal(Operator.Plus),
            Terminal(Operator.Div), Terminal(Operator.Mul), Terminal(";")
    )

    @Test
    fun paramList() = "ParamList" expected setOf(
            Terminal(")")
    )

    @Test
    fun tailParamList() = "TailParamList" expected setOf(
            Terminal(")")
    )

    private infix fun String.expected(expectedTerminals: Collection<Terminal>) {
        val set = YOLANG.followSetFor(GrammarSymbol.from(NonTerminal(this)))
        Assert.assertEquals(expectedTerminals.count(), set.count())
        Assert.assertEquals(expectedTerminals, set)
    }

}
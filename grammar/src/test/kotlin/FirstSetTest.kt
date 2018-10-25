import grammar.GrammarSymbol
import grammar.NonTerminal
import grammar.Terminal
import grammar.rules.*
import org.junit.Assert.assertEquals
import org.junit.Test

class FirstSetTest {

    @Test
    fun program() = "Program" expected setOf(
            Terminal.emptySymbol(), Terminal(Keyword.Function)
    )

    @Test
    fun functionList() = "FunctionList" expected setOf(
            Terminal.emptySymbol(), Terminal(Keyword.Function)
    )

    @Test
    fun function() = "Function" expected setOf(
            Terminal(Keyword.Function)
    )

    @Test
    fun paramDeclList() = "ParamDeclList" expected setOf(
            Terminal.emptySymbol(), Terminal(Literal.Identifier)
    )

    @Test
    fun tailParamDeclList() = "TailParamList" expected setOf(
            Terminal.emptySymbol(), Terminal(",")
    )

    @Test
    fun paramDecl() = "ParamDecl" expected setOf(
            Terminal(Literal.Identifier)
    )

    @Test
    fun type() = "Type" expected setOf(
            Terminal(Type.Int), Terminal(Type.Float), Terminal(Type.Boolean), Terminal(Type.Array)
    )

    @Test
    fun statement() = "Statement" expected setOf(
            Terminal(Literal.Identifier), Terminal(Keyword.Condition), Terminal("{"),
            Terminal(Keyword.Cycle), Terminal(Keyword.VariableDecl), Terminal(Keyword.Return)
    )

    @Test
    fun condition() = "Condition" expected setOf(
            Terminal(Keyword.Condition)
    )

    @Test
    fun optionalElse() = "OptionalElse" expected setOf(
            Terminal.emptySymbol(), Terminal("else")
    )

    @Test
    fun loop() = "Loop" expected setOf(
            Terminal(Keyword.Cycle)
    )

    @Test
    fun decl() = "Decl" expected setOf(
            Terminal(Keyword.VariableDecl)
    )

    @Test
    fun assign() = "Assign" expected setOf(
            Terminal(Literal.Identifier)
    )

    @Test
    fun `return`() = "Return" expected setOf(
            Terminal(Keyword.Return)
    )

    @Test
    fun compositeStatement() = "CompositeStatement" expected setOf(
            Terminal("{")
    )

    @Test
    fun statementList() = "StatementList" expected setOf(
            Terminal.emptySymbol(), Terminal(Literal.Identifier), Terminal(Keyword.Condition),
            Terminal(Keyword.Cycle), Terminal(Keyword.VariableDecl), Terminal(Keyword.Return),
            Terminal("{")
    )

    @Test
    fun expression() = "Expression" expected setOf(
            Terminal(Literal.Identifier), Terminal("("), Terminal(Operator.Minus),
            Terminal(Keyword.Yo), Terminal(Keyword.BooleanTrue), Terminal(Keyword.BooleanFalse),
            Terminal(Literal.Number)
    )

    @Test
    fun variableRead() = "VariableRead" expected setOf(
            Terminal(Literal.Identifier), Terminal(Keyword.Yo), Terminal(Keyword.BooleanTrue),
            Terminal(Keyword.BooleanFalse)
    )

    @Test
    fun mathExpression() = "MathExpression" expected setOf(
            Terminal(Literal.Identifier), Terminal("("), Terminal(Operator.Minus),
            Terminal(Keyword.Yo), Terminal(Keyword.BooleanTrue), Terminal(Keyword.BooleanFalse),
            Terminal(Literal.Number)
    )

    @Test
    fun mathExpressionExpr() = "MathExpression" expected setOf(
            Terminal(Literal.Identifier), Terminal("("), Terminal(Operator.Minus),
            Terminal(Keyword.Yo), Terminal(Keyword.BooleanTrue), Terminal(Keyword.BooleanFalse),
            Terminal(Literal.Number)
    )

    @Test
    fun mathExpressionTailExpr() = "MathExpressionTailExpr" expected setOf(
            Terminal.emptySymbol(), Terminal(Operator.Minus), Terminal(Operator.Plus)
    )

    @Test
    fun mathExpressionTerm() = "MathExpressionTerm" expected setOf(
            Terminal(Literal.Identifier), Terminal("("), Terminal(Operator.Minus),
            Terminal(Keyword.Yo), Terminal(Keyword.BooleanTrue), Terminal(Keyword.BooleanFalse),
            Terminal(Literal.Number)
    )

    @Test
    fun mathExpressionTailTerm() = "MathExpressionTailTerm" expected setOf(
            Terminal.emptySymbol(), Terminal(Operator.Mul), Terminal(Operator.Div)
    )

    @Test
    fun mathExpressionFactor() = "MathExpressionFactor" expected setOf(
            Terminal(Literal.Identifier), Terminal("("), Terminal(Operator.Minus),
            Terminal(Keyword.Yo), Terminal(Keyword.BooleanTrue), Terminal(Keyword.BooleanFalse),
            Terminal(Literal.Number)
    )

    @Test
    fun functionCall() = "FunctionCall" expected setOf(
            Terminal(Keyword.Yo)
    )

    @Test
    fun paramList() = "ParamList" expected setOf(
            Terminal.emptySymbol(), Terminal(Literal.Identifier), Terminal(Keyword.Yo),
            Terminal(Keyword.BooleanTrue), Terminal(Keyword.BooleanFalse)
    )

    @Test
    fun tailParamList() = "TailParamList" expected setOf(
            Terminal.emptySymbol(), Terminal(",")
    )

    private infix fun String.expected(expectedTerminals: Collection<Terminal>) {
        val set = YOLANG.firstSetFor(GrammarSymbol.from(NonTerminal(this)))
        assertEquals(expectedTerminals.count(), set.count())
        assertEquals(expectedTerminals, set)
    }

}
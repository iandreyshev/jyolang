import grammar.GrammarSymbol
import grammar.NonTerminal
import grammar.Terminal
import grammar.rules.YOLANG
import grammar.rules.Keyword
import grammar.rules.RuleName
import grammar.rules.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class FirstSetTest {

    @Test
    fun program() = "Program" expected setOf(
            Terminal(Keyword.EOF), Terminal(Keyword.Function)
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
    fun paramList() = "ParamList" expected setOf(
            Terminal.emptySymbol(), Terminal("id")
    )

    @Test
    fun tailParamList() = "TailParamList" expected setOf(
            Terminal.emptySymbol(), Terminal(",")
    )

    @Test
    fun param() = "Param" expected setOf(
            Terminal("id")
    )

    @Test
    fun type() = "Type" expected setOf(
            Terminal(Type.Int), Terminal(Type.Float), Terminal(Type.Boolean),
            Terminal(Type.Array)
    )

    @Test
    fun statement() = "Statement" expected setOf(
            Terminal(RuleName.IDENTIFIER), Terminal(Keyword.Condition), Terminal(Keyword.Cycle),
            Terminal(Keyword.VariableDecl), Terminal("return"), Terminal("{")
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
            Terminal("while")
    )

    @Test
    fun decl() = "Decl" expected setOf(
            Terminal("var")
    )

    @Test
    fun assign() = "Assign" expected setOf(
            Terminal("id")
    )

    @Test
    fun `return`() = "Return" expected setOf(
            Terminal("return")
    )

    @Test
    fun compositeStatement() = "CompositeStatement" expected setOf(
            Terminal("{")
    )

    @Test
    fun statementList() = "StatementList" expected setOf(
            Terminal.emptySymbol(), Terminal("id"), Terminal(Keyword.Condition), Terminal(Keyword.Cycle),
            Terminal("var"), Terminal("return"), Terminal("{")
    )

    @Test
    fun expression() = "Expression" expected setOf(
            Terminal("id"), Terminal("literal"), Terminal("true"), Terminal("false")
    )

    private infix fun String.expected(expectedTerminals: Collection<Terminal>) {
        val set = YOLANG.firstSetFor(GrammarSymbol.from(NonTerminal(this)))
        assertEquals(expectedTerminals.count(), set.count())
        assertEquals(expectedTerminals, set)
    }

}
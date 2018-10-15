import grammar.GrammarSymbol
import grammar.NonTerminal
import grammar.Terminal
import org.junit.Assert.assertEquals
import org.junit.Test

class FirstSetTest {

    @Test
    fun program() = "Program" expected setOf(
            Terminal(KEYWORDS.EOF), Terminal(KEYWORDS.Function)
    )

    @Test
    fun functionList() = "FunctionList" expected setOf(
            Terminal.emptySymbol(), Terminal(KEYWORDS.Function)
    )

    @Test
    fun function() = "Function" expected setOf(
            Terminal(KEYWORDS.Function)
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
            Terminal(KEYWORDS.TYPE.Int), Terminal(KEYWORDS.TYPE.Float), Terminal(KEYWORDS.TYPE.Boolean),
            Terminal(KEYWORDS.TYPE.Array)
    )

    @Test
    fun statement() = "Statement" expected setOf(
            Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.Cycle),
            Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{")
    )

    @Test
    fun condition() = "Condition" expected setOf(
            Terminal(KEYWORDS.ConditionIf)
    )

    @Test
    fun optionalElse() = "OptionalElse" expected setOf(
            Terminal.emptySymbol(), Terminal(KEYWORDS.ConditionElse)
    )

    @Test
    fun loop() = "Loop" expected setOf(
            Terminal(KEYWORDS.Cycle)
    )

    @Test
    fun decl() = "Decl" expected setOf(
            Terminal(KEYWORDS.Declaration)
    )

    @Test
    fun assign() = "Assign" expected setOf(
            Terminal("id")
    )

    @Test
    fun `return`() = "Return" expected setOf(
            Terminal(KEYWORDS.Return)
    )

    @Test
    fun compositeStatement() = "CompositeStatement" expected setOf(
            Terminal("{")
    )

    @Test
    fun statementList() = "StatementList" expected setOf(
            Terminal.emptySymbol(), Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.Cycle),
            Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{")
    )

    @Test
    fun expression() = "Expression" expected setOf(
            Terminal("id"), Terminal("literal"), Terminal("true"), Terminal("false")
    )

    private infix fun String.expected(expectedTerminals: Collection<Terminal>) {
        val set = JYOLANG.firstSetFor(GrammarSymbol.from(NonTerminal(this)))
        assertEquals(expectedTerminals.count(), set.count())
        assertEquals(expectedTerminals, set)
    }

}
import grammar.GrammarSymbol
import grammar.NonTerminal
import grammar.Terminal
import org.junit.Assert
import org.junit.Test

class FollowSetTest {

    @Test
    fun program() = "Program" expected setOf(
            Terminal.endOfInput()
    )

    @Test
    fun functionList() = "FunctionList" expected setOf(
            Terminal(KEYWORDS.EOF)
    )

    @Test
    fun function() = "Function" expected setOf(
            Terminal(KEYWORDS.Function), Terminal(KEYWORDS.EOF)
    )

    @Test
    fun paramList() = "ParamList" expected setOf(
            Terminal(")")
    )

    @Test
    fun tailParamList() = "TailParamList" expected setOf(
            Terminal(")")
    )

    @Test
    fun param() = "Param" expected setOf(
            Terminal(","), Terminal(")")
    )

    @Test
    fun type() = "Type" expected setOf(
            Terminal(","), Terminal(":"), Terminal(")"), Terminal(";"), Terminal(">")
    )

    @Test
    fun statement() = "Statement" expected setOf(
            Terminal(KEYWORDS.Function), Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.ConditionElse),
            Terminal(KEYWORDS.Cycle), Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{"),
            Terminal("}"), Terminal(KEYWORDS.EOF)
    )

    @Test
    fun condition() = "Condition" expected setOf(
            Terminal(KEYWORDS.Function), Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.ConditionElse),
            Terminal(KEYWORDS.Cycle), Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{"),
            Terminal("}"), Terminal(KEYWORDS.EOF)
    )

    @Test
    fun optionalElse() = "OptionalElse" expected setOf(
            Terminal(KEYWORDS.Function), Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.ConditionElse),
            Terminal(KEYWORDS.Cycle), Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{"),
            Terminal("}"), Terminal(KEYWORDS.EOF)
    )

    @Test
    fun loop() = "Loop" expected setOf(
            Terminal(KEYWORDS.Function), Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.ConditionElse),
            Terminal(KEYWORDS.Cycle), Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{"),
            Terminal("}"), Terminal(KEYWORDS.EOF)
    )

    @Test
    fun decl() = "Decl" expected setOf(
            Terminal(KEYWORDS.Function), Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.ConditionElse),
            Terminal(KEYWORDS.Cycle), Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{"),
            Terminal("}"), Terminal(KEYWORDS.EOF)
    )

    @Test
    fun assign() = "Assign" expected setOf(
            Terminal(KEYWORDS.Function), Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.ConditionElse),
            Terminal(KEYWORDS.Cycle), Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{"),
            Terminal("}"), Terminal(KEYWORDS.EOF)
    )

    @Test
    fun `return`() = "Return" expected setOf(
            Terminal(KEYWORDS.Function), Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.ConditionElse),
            Terminal(KEYWORDS.Cycle), Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{"),
            Terminal("}"), Terminal(KEYWORDS.EOF)
    )

    @Test
    fun compositeStatement() = "CompositeStatement" expected setOf(
            Terminal(KEYWORDS.Function), Terminal("id"), Terminal(KEYWORDS.ConditionIf), Terminal(KEYWORDS.ConditionElse),
            Terminal(KEYWORDS.Cycle), Terminal(KEYWORDS.Declaration), Terminal(KEYWORDS.Return), Terminal("{"),
            Terminal("}"), Terminal(KEYWORDS.EOF)
    )

    @Test
    fun statementList() = "StatementList" expected setOf(
            Terminal("}")
    )

    @Test
    fun expression() = "Expression" expected setOf(
            Terminal(")"), Terminal(";")
    )

    private infix fun String.expected(expectedTerminals: Collection<Terminal>) {
        val set = JYOLANG.followSetFor(GrammarSymbol.from(NonTerminal(this)))
        Assert.assertEquals(expectedTerminals.count(), set.count())
        Assert.assertEquals(expectedTerminals, set)
    }

}
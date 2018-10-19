import grammar.GrammarSymbol
import grammar.NonTerminal
import grammar.Terminal
import grammar.rules.YOLANG
import grammar.rules.Keyword
import grammar.rules.TypeName
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.ParsingTable

class ParsingTableTest {

    private val mTable = ParsingTable(YOLANG)

    @Test
    fun program() = testTableRow("Program") {
        Keyword.EOF expected listOf(
                nonTerminal("FunctionList"), terminal(Keyword.EOF)
        )
        Keyword.Function expected listOf(
                nonTerminal("FunctionList"), terminal(Keyword.EOF)
        )
    }

    @Test
    fun functionList() = testTableRow("FunctionList") {
        Keyword.EOF.expectedEmptySymbol()
        Keyword.Function expected listOf(
                nonTerminal("Function"), nonTerminal("FunctionList")
        )
    }

    @Test
    fun function() = testTableRow("Function") {
        Keyword.Function expected listOf(
                terminal(Keyword.Function), terminal("id"), terminal("("), nonTerminal("ParamList") ,terminal(")"),
                terminal("->"), nonTerminal("Type"), terminal(":"), nonTerminal("Statement")
        )
    }

    @Test
    fun paramList() = testTableRow("ParamList") {
        "id" expected listOf(
                nonTerminal("Param"), nonTerminal("TailParamList")
        )
        ")".expectedEmptySymbol()
    }

    @Test
    fun tailParamList() = testTableRow("TailParamList") {
        ")".expectedEmptySymbol()
        "," expected listOf(
                terminal(","), nonTerminal("Param"), nonTerminal("TailParamList")
        )
    }

    @Test
    fun param() = testTableRow("Param") {
        "id" expected listOf(
                terminal("id"), terminal(":"), nonTerminal("Type")
        )
    }

    @Test
    fun type() = testTableRow("Type") {
        TypeName.Int expected listOf(
                terminal(TypeName.Int)
        )
        TypeName.Float expected listOf(
                terminal(TypeName.Float)
        )
        TypeName.Boolean expected listOf(
                terminal(TypeName.Boolean)
        )
        TypeName.Array expected listOf(
                terminal(TypeName.Array), terminal("<"), nonTerminal("Type"), terminal(">")
        )
    }

    @Test
    fun statement() = testTableRow("Statement") {
        "id" expected listOf(
                nonTerminal("Assign")
        )
        Keyword.Condition expected listOf(
                nonTerminal("Condition")
        )
        Keyword.CycleWithPreCondition expected listOf(
                nonTerminal("Loop")
        )
        "var" expected listOf(
                nonTerminal("Decl")
        )
        "return" expected listOf(
                nonTerminal("Return")
        )
        "{" expected listOf(
                nonTerminal("CompositeStatement")
        )
    }

    @Test
    fun condition() = testTableRow("Condition") {
        Keyword.Condition expected listOf(
                terminal(Keyword.Condition), terminal("("), nonTerminal("Expression"), terminal(")"),
                nonTerminal("Statement"), nonTerminal("OptionalElse")
        )
    }

    @Test
    fun optionalElse() = testTableRow("OptionalElse") {
        Keyword.EOF.expectedEmptySymbol()
        Keyword.Function.expectedEmptySymbol()
        "id".expectedEmptySymbol()
        "if".expectedEmptySymbol()
        "else".expectedEmptySymbol()
        "while".expectedEmptySymbol()
    }

    private inner class TestBuilder(private val row: NonTerminal) {

        private val mTestData: MutableMap<Terminal, List<GrammarSymbol>> = mutableMapOf()

        fun test() {
            mTestData.forEach { data ->
                val productionFromTable = mTable[row, data.key]?.symbols
                assertEquals(data.value.count(), productionFromTable?.count())
                assertEquals(data.value.toHashSet(), productionFromTable?.toHashSet())
            }

            YOLANG.terminals.toMutableList().apply {
                removeAll(mTestData.keys)
            }.forEach { emptyColumn ->
                val tableCell = mTable[row, emptyColumn]
                assertEquals(null, tableCell)
            }
        }

        infix fun String.expected(symbols: List<GrammarSymbol>) {
            mTestData[Terminal(this)] = symbols
        }

        fun String.expectedEmptySymbol() {
            mTestData[Terminal(this)] = listOf(Terminal.emptySymbol().toSymbol())
        }

        fun terminal(literal: String) = Terminal(literal).toSymbol()

        fun nonTerminal(literal: String) = NonTerminal(literal).toSymbol()

    }

    private fun testTableRow(nonTerminal: String, director: TestBuilder.() -> Unit) {
        TestBuilder(NonTerminal(nonTerminal)).apply(director).test()
    }

}
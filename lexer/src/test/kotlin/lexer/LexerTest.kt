package lexer

import grammar.SymbolType
import org.junit.Assert.*
import org.junit.Test
import token.Token

class LexerTest {

    @Test
    fun emptyText() = "" reproduce listOf()

    @Test
    fun blankText() = """



    """.trimIndent() reproduce listOf()

    @Test
    fun function() = "func Identifier():Int->" reproduce listOf(
            Token("func", SymbolType.KEYWORD_FUNCTION, 0),
            Token("Identifier", SymbolType.IDENTIFIER, 5),
            Token("(", SymbolType.WORK_OPERATOR, 15),
            Token(")", SymbolType.WORK_OPERATOR, 16),
            Token(":", SymbolType.WORK_OPERATOR, 17),
            Token("Int", SymbolType.TYPE_INT, 18),
            Token("->", SymbolType.WORK_OPERATOR, 21)
    )

    @Test
    fun number() = "   12345" reproduce listOf(
            Token("12345", SymbolType.NUMBER, 3)
    )

    private infix fun String.reproduce(expected: List<Token>) {
        val lexer = Lexer(this)
        val tokensFromLexer = mutableListOf<Token>()

        lexer.forEach {
            println("Add $it")
            tokensFromLexer.add(it)
        }

        assertEquals(expected.count(), tokensFromLexer.count())

        var index = 0
        tokensFromLexer.forEach {
            assertEquals(it, expected[index])
            ++index
        }
    }

}
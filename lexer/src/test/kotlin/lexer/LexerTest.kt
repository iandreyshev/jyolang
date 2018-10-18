package lexer

import org.junit.Assert.*
import org.junit.Test

class LexerTest {

    @Test
    fun someTest() = test("func f ds fjds jflds jlf jdslkjfds")

    private fun test(text: String) {
        val lexer = Lexer(text)

        while (true) {
            val token = lexer.read()
            println(token)

            if (token.isEnd) {
                break
            }
        }
    }

}
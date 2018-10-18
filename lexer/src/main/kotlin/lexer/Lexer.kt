package lexer

import token.Token
import java.util.regex.Pattern

class Lexer(private val text: String) {

    private var mCaretPosition: Int = 0
    private val mIsEOF: Boolean
        get() = mCaretPosition >= text.length

    fun read(): Token {
        skipGarbage()
        return when {
            mIsEOF -> eof()
            now(TokenPattern.INVALID_IDENTIFIER) -> undefined()
            now(TokenPattern.IDENTIFIER) && now(TokenPattern.LETTER_OPERATOR) -> letterOperator()
            now(TokenPattern.IDENTIFIER) -> identifier()
            now(TokenPattern.NUMBER) -> number()
            now(TokenPattern.SYMBOL_OPERATOR) -> symbolOperator()
            now(TokenPattern.WORK_SYMBOLS) -> workSymbol()
            else -> undefined()
        }
    }

    private fun skipGarbage(): Boolean {
        var isSkipped = false

        while (!mIsEOF) {
            if (text.first().isWhitespace()) {
                isSkipped = true
                continue
            } else if (now(TokenPattern.COMMENT)) {
                isSkipped = true
                continue
            }

            break
        }

        return isSkipped
    }

    private fun now(pattern: Pattern): Boolean {
        return pattern.matcher(text).find(mCaretPosition)
    }

    private fun getBy(pattern: Pattern): String {
        val string: String = pattern.matcher(text).group(0)
        mCaretPosition += string.length
        return string
    }

    private fun eof(): Token = Token("EOF", true)

    private fun identifier(): Token = Token(getBy(TokenPattern.IDENTIFIER))

    private fun letterOperator(): Token = Token(getBy(TokenPattern.LETTER_OPERATOR))

    private fun number(): Token = Token(getBy(TokenPattern.NUMBER))

    private fun symbolOperator(): Token = Token(getBy(TokenPattern.SYMBOL_OPERATOR))

    private fun workSymbol(): Token = Token(getBy(TokenPattern.WORK_SYMBOLS))

    private fun undefined(): Token = Token("Undefined ${text.first()}", true)

}

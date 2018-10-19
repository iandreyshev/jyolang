package lexer

import grammar.SymbolType
import grammar.rules.Keyword
import token.Token
import java.lang.Exception

class Lexer(private val text: String): Iterator<Token> {

    private var mCaretPosition: Int = 0
    private val mIsEOF: Boolean
        get() = mCaretPosition >= text.length
    private var mToken: Token = Token.eof()

    override fun hasNext(): Boolean = !mIsEOF

    override fun next(): Token = read()

    fun read(): Token {
        skipGarbage()

        return when {
            mIsEOF -> Token.eof()

            TokenPattern.INVALID_IDENTIFIER canTake ::undefined
                    || now(TokenPattern.IDENTIFIER) && TokenPattern.LETTER_OPERATOR canTake ::letterOperator
                    || TokenPattern.IDENTIFIER canTake ::identifier
                    || TokenPattern.NUMBER canTake ::number
                    || TokenPattern.SYMBOL_OPERATOR canTake ::symbolOperator
                    || TokenPattern.WORK_SYMBOL canTake ::workSymbol -> mToken

            else -> Token(text.take(1), SymbolType.UNDEFINED)
        }
    }

    private fun skipGarbage(): Boolean {
        var caretPositionBefore = mCaretPosition

        while (!mIsEOF) {
            if (text.first().isWhitespace()) {
                ++caretPositionBefore
                continue
            } else {
                val comment = getBy(TokenPattern.COMMENT)
                if (comment != null) {
                    mCaretPosition += comment.length
                    continue
                }
            }
            break
        }

        return caretPositionBefore != mCaretPosition
    }

    private fun now(pattern: String): Boolean =
            pattern.toPattern().matcher(text).find(mCaretPosition)

    private fun getBy(pattern: String): String? = try {
        val patternFromStart = "^($pattern)".toPattern()
        val textToRead = text.subSequence(mCaretPosition, text.length)
        patternFromStart.matcher(textToRead).let {
            println(text.subSequence(mCaretPosition, text.length))
            it.lookingAt()
            it.group(0)
        }
    } catch (ex: Exception) {
        null
    }

    private infix fun String.canTake(constructor: (String) -> Unit): Boolean {
        getBy(this)?.let {
            mCaretPosition += it.length
            constructor(it)
            return true
        }
        return false
    }

    private fun identifier(literal: String) {
        mToken = Token(literal, SymbolType.IDENTIFIER, positionBefore(literal))
    }

    private fun letterOperator(literal: String) {
        mToken = when (literal) {
            Keyword.Function -> Token(literal, SymbolType.KEYWORD_FUNCTION, positionBefore(literal))
            else -> Token(literal, SymbolType.UNDEFINED, positionBefore(literal))
        }
    }

    private fun number(literal: String) {
        mToken = Token(literal, SymbolType.NUMBER, positionBefore(literal))
    }

    private fun symbolOperator(literal: String) {
        mToken = Token(literal, SymbolType.OPERATOR, positionBefore(literal))
    }

    private fun workSymbol(literal: String) {
        mToken = Token(literal, SymbolType.WORK_OPERATOR, positionBefore(literal))
    }

    private fun undefined(literal: String) {
        mToken = Token("Undefined $literal", SymbolType.UNDEFINED, positionBefore(literal))
    }

    private fun positionBefore(literal: String) = mCaretPosition - literal.length

}

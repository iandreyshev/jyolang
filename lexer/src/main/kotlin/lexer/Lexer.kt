package lexer

import grammar.SymbolType
import grammar.rules.Keyword
import grammar.rules.Type
import token.Token
import java.lang.Exception

class Lexer(private val text: String) : Iterator<Token> {

    private var mCaretPosition = 0
    private var mSeparatorPassed = true
    private val mIsEOF: Boolean
        get() = mCaretPosition >= text.length
    private var mToken: Token = Token("", SymbolType.UNDEFINED)

    override fun hasNext(): Boolean {
        skipGarbage()
        return !mIsEOF
    }

    override fun next(): Token {
        if (mIsEOF) {
            return Token.eof(mCaretPosition)
        }
        return read()
    }

    private fun read(): Token {
        if (skipGarbage()) {
            mSeparatorPassed = true
        }

        if (mIsEOF) {
            return mToken
        }

        when {
            TokenPattern.INVALID_IDENTIFIER take ::undefined -> {
                // Do nothing
            }
            now(TokenPattern.IDENTIFIER) && (
                    TokenPattern.LETTER_OPERATOR take ::letterOperator
                            || TokenPattern.TYPE_NAME take ::typeName
                            || TokenPattern.IDENTIFIER take ::identifier) -> {
                mSeparatorPassed = false
            }
            TokenPattern.NUMBER take ::number -> {
                mSeparatorPassed = false
            }
            TokenPattern.SYMBOL_OPERATOR take ::symbolOperator -> {
                mSeparatorPassed = true
            }
            else -> Token(text.take(1), SymbolType.UNDEFINED)
        }

        return mToken
    }

    private fun skipGarbage(): Boolean {
        val position = mCaretPosition

        while (!mIsEOF) {
            if (text[mCaretPosition].isWhitespace()) {
                ++mCaretPosition
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

        return position != mCaretPosition
    }

    private fun now(pattern: String): Boolean =
            pattern.toPattern().matcher(text).find(mCaretPosition)

    private fun getBy(pattern: String): String? = try {
        val patternFromStart = "^($pattern)".toPattern()
        val textToRead = text.subSequence(mCaretPosition, text.length)
        patternFromStart.matcher(textToRead).let {
            it.lookingAt()
            it.group(0)
        }
    } catch (ex: Exception) {
        null
    }

    private infix fun String.take(constructor: (String) -> Unit): Boolean {
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
        val type = when (literal) {
            Keyword.Function -> SymbolType.KEYWORD_FUNCTION
            Keyword.Condition -> SymbolType.KEYWORD_IF
            Keyword.ConditionElse -> SymbolType.KEYWORD_ELSE
            Keyword.Cycle -> SymbolType.KEYWORD_WHILE
            Keyword.VariableDecl -> SymbolType.KEYWORD_VAR
            Keyword.Return -> SymbolType.KEYWORD_RETURN
            else -> SymbolType.UNDEFINED
        }
        mToken = Token(literal, type, positionBefore(literal))
    }

    private fun typeName(literal: String) {
        val type = when (literal) {
            Type.Int -> SymbolType.TYPE_INT
            Type.Float -> SymbolType.TYPE_FLOAT
            Type.Boolean -> SymbolType.TYPE_BOOLEAN
            Type.Array -> SymbolType.TYPE_ARRAY
            else -> SymbolType.UNDEFINED
        }
        mToken = Token(literal, type, positionBefore(literal))
    }

    private fun number(literal: String) {
        mToken = Token(literal, SymbolType.NUMBER, positionBefore(literal))
    }

    private fun symbolOperator(literal: String) {
        mToken = Token(literal, SymbolType.OPERATOR, positionBefore(literal))
    }

    private fun undefined(literal: String) {
        mToken = Token("Undefined $literal", SymbolType.UNDEFINED, positionBefore(literal))
    }

    private fun positionBefore(literal: String) = mCaretPosition - literal.length

}

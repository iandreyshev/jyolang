package lexer

import grammar.SymbolType
import grammar.rules.Keyword
import grammar.rules.Type
import token.Token
import java.lang.Exception

class Lexer(private val text: String) : Iterator<Token> {

    private val mIsEOF: Boolean
        get() = mCaretPosition >= text.length
    private val mTakeActions = listOf(
            ::takeInvalidIdentifier,
            ::takeIdentifier,
            ::takeNumber,
            ::takeSymbolOperator,
            ::takeUndefined
    )

    private var mCaretPosition = 0
    private var mLastToken: Token = Token("", SymbolType.UNDEFINED)

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
        skipGarbage()

        if (mIsEOF) {
            return mLastToken
        }

        mTakeActions.any { takeFunction ->
            takeFunction()
        }

        return mLastToken
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

    private fun takeInvalidIdentifier(): Boolean {
        val invalidLiteral = getBy(TokenPattern.INVALID_IDENTIFIER) ?: return false
        mCaretPosition += invalidLiteral.length
        mLastToken = Token(invalidLiteral, SymbolType.UNDEFINED, positionOf(invalidLiteral))
        return true
    }

    private fun takeIdentifier(): Boolean {
        val identifier = getBy(TokenPattern.IDENTIFIER) ?: return false

        return takeLetterOperatorFrom(identifier)
                || takeTypeName(identifier)
                || takeIdentifier(identifier)
    }

    private fun takeLetterOperatorFrom(identifier: String): Boolean {
        val literal = getBy(TokenPattern.LETTER_OPERATOR)

        if (literal?.length != identifier.length) {
            return false
        }

        val type = when (identifier) {
            Keyword.Function -> SymbolType.KEYWORD_FUNCTION
            Keyword.Condition -> SymbolType.KEYWORD_IF
            Keyword.ConditionElse -> SymbolType.KEYWORD_ELSE
            Keyword.Cycle -> SymbolType.KEYWORD_WHILE
            Keyword.VariableDecl -> SymbolType.KEYWORD_VAR
            Keyword.Return -> SymbolType.KEYWORD_RETURN
            else -> return false
        }

        mCaretPosition += literal.length
        mLastToken = Token(literal, type, positionOf(literal))

        return true
    }

    private fun takeTypeName(identifier: String): Boolean {
        val literal = getBy(TokenPattern.TYPE_NAME)

        if (literal?.length != identifier.length) {
            return false
        }

        val type = when (literal) {
            Type.Int -> SymbolType.TYPE_INT
            Type.Float -> SymbolType.TYPE_FLOAT
            Type.Boolean -> SymbolType.TYPE_BOOLEAN
            Type.Array -> SymbolType.TYPE_ARRAY
            else -> return false
        }

        mCaretPosition += literal.length
        mLastToken = Token(literal, type, positionOf(literal))

        return true
    }

    private fun takeIdentifier(identifier: String): Boolean {
        mCaretPosition += identifier.length
        mLastToken = Token(identifier, SymbolType.IDENTIFIER, positionOf(identifier))
        return true
    }

    private fun takeNumber(): Boolean {
        val literal = getBy(TokenPattern.NUMBER) ?: return false
        mCaretPosition += literal.length
        mLastToken = Token(literal, SymbolType.NUMBER, positionOf(literal))
        return true
    }

    private fun takeSymbolOperator(): Boolean {
        val literal = getBy(TokenPattern.SYMBOL_OPERATOR) ?: return false
        mCaretPosition += literal.length
        mLastToken = Token(literal, SymbolType.SYMBOL_OPERATOR, positionOf(literal))
        return true
    }

    private fun takeUndefined(): Boolean {
        val literal = text[mCaretPosition].toString()
        mCaretPosition += literal.length
        mLastToken = Token(literal, SymbolType.UNDEFINED, positionOf(literal))
        return true
    }

    private fun positionOf(literal: String) = mCaretPosition - literal.length

}

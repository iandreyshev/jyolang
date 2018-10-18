package lexer

import java.util.regex.Pattern

internal object TokenPattern {

    private const val MAX_IDENTIFIER_SIZE = 120

    val IDENTIFIER = patternFrom("[_a-zA-Z][_a-zA-Z0-9]{1,${MAX_IDENTIFIER_SIZE - 1}}")
    val NUMBER = patternFrom("([1-9][0-9]*)|[0-9]")
    val LETTER_OPERATOR = patternFrom("if|else|while|var|func")
    val SYMBOL_OPERATOR = patternFrom("\\+|\\-|\\*|\\/|\\=")
    val WORK_SYMBOLS = patternFrom("\\:|\\,|\\;|->|\\[|\\]|\\(|\\)|\\{|\\}")

    val COMMENT = patternFrom("\\/\\/(.|\n^ \n)*")

    val INVALID_IDENTIFIER = patternFrom("[0-9]+${TokenPattern.IDENTIFIER.pattern()}")

    private fun patternFrom(pattern: String): Pattern =
            "^($pattern)".toPattern()

}
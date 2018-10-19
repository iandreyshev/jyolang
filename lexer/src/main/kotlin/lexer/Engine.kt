package lexer

import token.TokenProperties
import java.lang.Exception

internal class Engine(private val text: String) {

    fun takeFrom(position: Int, regex: String): TokenProperties? = try {
        val string = text.toRegex().toPattern()
                .matcher(text)
                .region(position, text.length)
                .group(1)
        TokenProperties(string)
    } catch (ex: Exception) {
        null
    }

}
package token

internal data class TokenProperties(
        val string: String
) {

    val lingth: Int
        get() = string.length

}

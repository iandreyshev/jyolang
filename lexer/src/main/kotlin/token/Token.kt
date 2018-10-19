package token

data class Token(
        var type: TokenType,
        var literal: String = "",
        var position: Int = 0,
        var length: Int = 0,
        var rowNumber: Int = 0
)

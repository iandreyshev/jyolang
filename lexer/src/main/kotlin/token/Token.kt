package token

data class Token(
        var terminal: String,
        var isEnd: Boolean = false,
        var position: Int = 0,
        var length: Int = 0,
        var rowNumber: Int = 0
)

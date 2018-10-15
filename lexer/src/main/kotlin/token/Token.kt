package token

import grammar.Terminal

data class Token(
        var terminal: Terminal,
        var position: Int,
        var length: Int,
        var rowNumber: Int
)

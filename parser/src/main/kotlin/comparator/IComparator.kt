package parser.comparator

import grammar.Terminal
import token.Token

interface IComparator {
    fun compare(terminal: Terminal, token: Token): Boolean
}

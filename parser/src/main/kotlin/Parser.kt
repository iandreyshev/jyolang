package parser

import grammar.*
import parser.comparator.IComparator
import token.Token
import java.util.*

class Parser(
        private val root: GrammarSymbol,
        private val table: ParsingTable,
        private val comparator: IComparator
) {

    fun execute(lexer: Iterator<Token>) {
        val stack = Stack<GrammarSymbol>()
        stack.add(Terminal.endOfInput().toSymbol())
        stack.add(root)
        var token = lexer.next()
        var tokenNumber = 0

        while (stack.peek().terminal?.isDollar != true) {
            val topTerminal: Terminal? by lazy {
                stack.peek().terminal
            }
            val topNonTerminal: NonTerminal? by lazy {
                stack.peek().nonTerminal
            }
            val productionFromTable: Production? by lazy {
                table[topNonTerminal ?: return@lazy null, token.terminal]
            }

            println()
            println("stack: ${stack.joinToString(" ")}")
            println("stackTopTerminal: '$topTerminal'")
            println("stackTopNonTerminal: '$topNonTerminal'")
            println("productionFromTable: '$productionFromTable'")
            println("token: '$token'")
            println("tokenNumber: '$tokenNumber'")

            when {
                topTerminal != null && comparator.compare(topTerminal, token) -> {
                    stack.pop()
                    token = lexer.next()
                    ++tokenNumber
                }
                topNonTerminal == null -> throw ParserException(
                        position = tokenNumber,
                        given = token.terminal.literal,
                        expected = listOf(stack.peek().toString()))
                productionFromTable == null -> throw ParserException(
                        position = tokenNumber,
                        given = token.terminal.literal,
                        expected = table.productionsFor(topNonTerminal).keys
                                .filter { it != Grammar.END_OF_INPUT_SYMBOL })
                else -> {
                    stack.pop()
                    if (!productionFromTable.isEpsilon) {
                        productionFromTable.symbols.reversed().onEach(stack::addElement)
                    }
                }
            }
        }
    }

}

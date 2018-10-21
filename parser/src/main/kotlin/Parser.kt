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
        var tokenPosition = 0

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
            println(stack.joinToString(" "))
            println("topTerminal '$topTerminal'")
            println("topNonTerminal '$topNonTerminal'")
            println("productionFromTable '$productionFromTable'")
            println("tokenPosition token '$tokenPosition' '$token'")
            println("stack.peek().terminal '${stack.peek().terminal}'")

            when {
                topTerminal != null && comparator.compare(topTerminal, token) -> {
                    stack.pop()
                    token = lexer.next()
                    ++tokenPosition
                }
                topNonTerminal == null ->
                    throw ParserException(tokenPosition, stack.peek().toString())
                productionFromTable == null -> {
                    val expectedSymbols = table.productionsFor(topNonTerminal).keys
                            .filter { it != Grammar.END_OF_INPUT_SYMBOL }
                    throw ParserException(tokenPosition, token.terminal.literal, expectedSymbols)
                }
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

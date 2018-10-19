package parser

import grammar.*
import token.Token
import java.util.*

class Parser {
    fun execute(root: GrammarSymbol, table: ParsingTable, lexer: Iterator<Token>) {
        val stack = Stack<GrammarSymbol>()
        stack.add(Terminal.endOfInput().toSymbol())
        stack.add(root)
        var token = lexer.next()
        var tokenPosition = 0

        while (stack.peek().terminal?.isDollar != true) {
            val topNonTerminal: NonTerminal? by lazy {
                stack.peek().nonTerminal
            }
            val productionFromTable: Production? by lazy {
                table[topNonTerminal ?: return@lazy null, token.terminal]
            }

            println()
//            println(stack.joinToString(" "))
//            println(lexer.toString())
            println("topNonTerminal '$topNonTerminal'")
            println("productionFromTable '$productionFromTable'")
            println("tokenPosition token '$tokenPosition' '$token'")
            println("stack.peek().terminal '${stack.peek().terminal}")

            when {
                stack.peek().terminal?.type == token.type -> {
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

package parser

import extension.addToList
import grammar.*
import grammar.rules.*

typealias TableMap = MutableMap<String, MutableMap<String, Production?>>

class ParsingTable(
        private val grammar: Grammar
) {

    private val mTableMap: TableMap

    init {
        fun newTerminalToProductionMap() = grammar.terminals
                .map { it.literal }
                .addToList(Grammar.END_OF_INPUT_SYMBOL)
                .associate<String, String, Production?> { it to null }
                .toMutableMap()

        val emptyTable = grammar.nonTerminals
                .map { it.literal }
                .associate { nonTerminalSymbol ->
                    nonTerminalSymbol to newTerminalToProductionMap()
                }
                .toMutableMap()

        mTableMap = fill(emptyTable)
    }

    operator fun get(nonTerminal: NonTerminal, terminal: Terminal): Production? =
            when (terminal.type) {
                SymbolType.IDENTIFIER -> mTableMap[nonTerminal.literal]?.get(Literal.Identifier)
                SymbolType.NUMBER -> mTableMap[nonTerminal.literal]?.get(Literal.Number)
                else -> mTableMap[nonTerminal.literal]?.get(terminal.literal)
            }

    private fun fill(table: TableMap): TableMap {
        grammar.rules.rulesList.forEach { rule ->
            for (production in rule.productions) {
                val firstSet = grammar.firstSetFor(production.symbols)
                for (terminal in firstSet) {
                    table.put(rule.nonTerminal, terminal, production)
                }

                val hasEmptyInFirstSet = firstSet.contains(Terminal.emptySymbol())
                if (hasEmptyInFirstSet) {
                    val followSet = grammar.followSetFor(rule.nonTerminal.toSymbol())
                    for (terminal in followSet) {
                        table.put(rule.nonTerminal, terminal, production)
                    }

                    val hasEndOfInputInFollowSet = followSet.contains(Terminal.endOfInput())
                    if (hasEndOfInputInFollowSet) {
                        table.put(rule.nonTerminal, Terminal.endOfInput(), production)
                    }
                }
            }
        }

        return table
    }

    fun productionsFor(nonTerminal: NonTerminal): Map<String, Production?> =
            mTableMap[nonTerminal.literal]
                    ?.filter { it.value != null }!!

    private fun TableMap.put(nonTerminal: NonTerminal, terminal: Terminal, newProduction: Production) {
        val terminalIndex = if (terminal.isEpsilon) {
            Terminal.endOfInput()
        } else {
            terminal
        }

        get(nonTerminal.literal)
                ?.putIfAbsent(terminalIndex.literal, newProduction)
    }

}

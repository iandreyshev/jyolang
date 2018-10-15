package extension.print

import grammar.Grammar

private const val EPSILON = "ε"

/**
 * Print grammar for http://andrewduncan.net/parsing/index.html
 */
fun Grammar.toAndrewDuncanString(): String {
    var result = ""

    rules.rulesList.forEach { rule ->
        val nonTerminal = rule.nonTerminal
        rule.productions.forEach { production ->
            result += "$nonTerminal -> "
            result += production.symbols.joinToString(separator = " ") {
                if (it.terminal?.isEpsilon == true) {
                    EPSILON
                } else {
                    it.toString()
                }
            }
            result += "\n"
        }
    }

    return result
}

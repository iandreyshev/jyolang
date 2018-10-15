package extension.print

import grammar.*

private const val EPSILON = "''"

/**
 * http://jsmachines.sourceforge.net/machines/ll1.html
 */
fun Grammar.toJsmachinesString(): String {
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

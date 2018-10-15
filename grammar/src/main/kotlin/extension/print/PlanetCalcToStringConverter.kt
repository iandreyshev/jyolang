package extension.print

import grammar.*

/**
 * https://planetcalc.com/5600/
 */
fun Grammar.toPlanetCalcString(): String {
    var result = ""

    rules.rulesList.forEach { rule ->
        val nonTerminal = rule.nonTerminal
        result += "$nonTerminal="
        result += rule.productions.joinToString("|") { production ->
            production.symbols.joinToString(",") {
                when {
                    it.terminal?.isEpsilon == true -> ""
                    it.isTerminal -> "\"$it\""
                    else -> it.toString()
                }
            }
        }
        result += ";\n"
    }

    return result
}

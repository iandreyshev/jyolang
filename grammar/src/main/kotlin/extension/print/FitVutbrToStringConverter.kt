package extension.print

import grammar.*

fun Grammar.toFitVutbrToString(): String {
    val terminalsMap = mutableMapOf<Terminal, String>()
    var terminalId = 0
    var result = "%tokens " + terminals.joinToString(" ") {
        if (it.isEpsilon) ""
        else {
            val literal = "terminal_" + terminalId.toString(16)
            ++terminalId
            terminalsMap[it] = literal
            literal
        }
    }
    result += "\n%% /* LL(1) */\n"

    rules.rulesList.forEach { rule ->
        result += "${rule.nonTerminal} : "

        val productions = mutableListOf<String>()

        if (rule.productions.firstOrNull { it.isEpsilon } != null) {
            productions.add("/*eps*/")
        }

        val prodsWithoutEpsilon = rule.productions
                .filter { !it.isEpsilon }
                .map { production ->
                    production.symbols.joinToString(" ") {
                        if (it.isTerminal) {
                            terminalsMap[it.terminal]?.toString()!!
                        } else {
                            it.toString()
                        }
                    }
                }

        productions.addAll(prodsWithoutEpsilon)

        result += productions.joinToString("\n  | ")
        result += " ;\n"
    }

    result += "\n"

    terminalsMap.forEach { t, u ->
        result += "$t   $u"
    }

    return result
}

package dsl

import grammar.*
import grammar.dsl.GDSLGrammarSymbol
import grammar.rules.Literal
import grammar.rules.RuleName

typealias GDSLProduction = List<GDSLGrammarSymbol>
typealias GDSLAlternatives = List<GDSLProduction>
typealias GDSLRule = Pair<String, GDSLAlternatives>

fun grammarOf(director: IGDSLRuleBuilder.() -> Unit): Grammar {
    val builder = GDSLRuleBuilder().apply(director)
    val gcRules = builder.build()
    val gcNonTerminalSymbols = mutableSetOf<String>()
    val gcNewRules: MutableList<Pair<NonTerminal, MutableList<Production>>> = mutableListOf()

    gcRules.forEach {
        gcNonTerminalSymbols.add(it.first)
        gcNewRules.add(NonTerminal(it.first) to mutableListOf())
    }

    gcRules.map { gcRule ->
        val gcRuleNonTerminal = gcRule.first
        val gcAlternatives = gcRule.second
        val productions = gcAlternatives.map { gcProduction ->
            val grammarSymbols = gcProduction.map { gcSymbol ->
                if (gcNonTerminalSymbols.contains(gcSymbol.symbol)) {
                    GrammarSymbol.from(NonTerminal(gcSymbol.symbol))
                } else {
                    val terminal = Terminal(gcSymbol.symbol, gcSymbol.type ?: SymbolType.UNDEFINED)
                    GrammarSymbol.from(terminal)
                }
            }
            Production(grammarSymbols)
        }
        gcNewRules.firstOrNull { it.first.literal == gcRuleNonTerminal }
                ?.second
                ?.addAll(productions)
    }

    val rulesList = gcNewRules.map {
        Rule(it.first, it.second)
    }

    return Grammar(rulesList)
}

fun grammarRules(director: IGDSLRuleBuilder.() -> Unit): List<GDSLRule> =
        GDSLRuleBuilder().apply(director).build()

interface IGDSLRuleBuilder {
    fun nonTerminal(symbol: String, director: IGDSLProductionBuilder.() -> Unit)
    fun rules(rules: List<GDSLRule>)
}

class GDSLRuleBuilder : IGDSLRuleBuilder {

    private val mRules = mutableListOf<GDSLRule>()

    override fun nonTerminal(symbol: String, director: IGDSLProductionBuilder.() -> Unit) {
        val builder = GDSLProductionBuilder().apply(director)
        mRules.add(symbol to builder.build())
    }

    override fun rules(rules: List<GDSLRule>) {
        mRules.addAll(rules)
    }

    internal fun build(): List<GDSLRule> = mRules

}

interface IGDSLProductionBuilder {
    fun reproducedRulesSequence(vararg symbols: String)
    fun reproducedSymbol(symbol: String, type: SymbolType)
    fun reproducedSymbol(symbol: GDSLGrammarSymbol)
    fun reproducedSymbolsSequence(vararg symbols: GDSLGrammarSymbol)
    fun reproducedEmptySymbol()

    infix fun String.with(type: SymbolType) = GDSLGrammarSymbol(this, type)
    fun rule(ruleSymbol: String) = GDSLGrammarSymbol(ruleSymbol)
    fun operator(symbol: String) = symbol with SymbolType.OPERATOR
    fun identifier() = Literal.Identifier with SymbolType.IDENTIFIER
    fun number() = Literal.Number with SymbolType.NUMBER
}

private class GDSLProductionBuilder : IGDSLProductionBuilder {

    private val mProductionsSymbols = mutableListOf<GDSLProduction>()

    override fun reproducedRulesSequence(vararg symbols: String) {
        mProductionsSymbols.add(symbols.map { GDSLGrammarSymbol(it) })
    }

    override fun reproducedSymbol(symbol: String, type: SymbolType) {
        mProductionsSymbols.add(listOf(GDSLGrammarSymbol(symbol, type)))
    }

    override fun reproducedSymbol(symbol: GDSLGrammarSymbol) {
        reproducedSymbolsSequence(symbol)
    }

    override fun reproducedSymbolsSequence(vararg symbols: GDSLGrammarSymbol) {
        mProductionsSymbols.add(symbols.toList())
    }

    override fun reproducedEmptySymbol() {
        val emptySymbol = GDSLGrammarSymbol(Terminal.emptySymbol().literal)
        mProductionsSymbols.add(listOf(emptySymbol))
    }

    fun build(): GDSLAlternatives = mProductionsSymbols

}
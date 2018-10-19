import dsl.GDSLRule
import dsl.grammarOf
import grammar.Grammar
import grammar.rules.*
import parser.Parser
import parser.ParsingTable

abstract class GrammarParsingTest(private val grammar: Grammar) {

    constructor(rules: List<GDSLRule>)
            : this(grammarOf { rules(rules) })

    private val mTable = ParsingTable(grammar)
    private val mParser: Parser = Parser()

    protected fun parse(text: String) {
        try {
            mParser.execute(grammar.root, mTable, TestLexer(text + " ${Keyword.EOF}"))
            println("Input is OK")
        } catch (ex: Exception) {
            println("Error: ${ex.message}")
        }
        println()
    }

}
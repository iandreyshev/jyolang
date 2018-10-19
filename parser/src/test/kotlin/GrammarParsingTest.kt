import dsl.GDSLRule
import dsl.grammarOf
import grammar.Grammar
import lexer.Lexer
import parser.Parser
import parser.ParsingTable

abstract class GrammarParsingTest(private val grammar: Grammar) {

    constructor(rules: List<GDSLRule>)
            : this(grammarOf { rules(rules) })

    private val mTable = ParsingTable(grammar)
    private val mParser: Parser = Parser()

    protected fun parse(text: String) {
        try {
            mParser.execute(grammar.root, mTable, Lexer(text))
            println("Input is OK")
        } catch (ex: Exception) {
            println("Error: ${ex.message}")
        }
        println()
    }

}
import grammar.Grammar
import lexer.Lexer
import parser.Parser
import parser.ParsingTable
import parser.comparator.Comparator

abstract class GrammarParsingTest(grammar: Grammar) {

    private val mTable = ParsingTable(grammar)
    private val mParser: Parser = Parser(grammar.root, mTable, Comparator)

    protected fun parse(text: String) {
        try {
            val lexer = Lexer(text)
            mParser.execute(lexer)
        } catch (ex: Exception) {
            assert(false) {
                ex.message ?: ex
            }
        }
        println()
    }

}
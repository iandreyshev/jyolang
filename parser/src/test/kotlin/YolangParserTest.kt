import grammar.rules.YOLANG
import org.junit.Test

// TODO: Доклад про AST в .NET из GoogleDrive
// TODO: Во понедельник в 13 00 лекция заканчивается в 522
class YolangParserTest : GrammarParsingTest(YOLANG) {

    @Test
    fun decl() = parse("""
        func Identifier ( ) -> Int : {
            var Identifier : Array < Int > ;
            return Identifier ;
        }
        """.trimIndent())

    @Test
    fun loop() = parse("""
        func id ( id : Float ) -> Array < Bool > : {
            var id : Int ;
                id = FloatLiteral ;
            while ( true ) {
                var id : Float ;
                id = - IntegerLiteral ;
            }
        }
        """.trimIndent())

    @Test
    fun arraysInArray() = parse("""
        func id ( id : Int , id : Float ) -> Int : {
            var id : Bool ;
            return id ;
        }

        func id ( id : Array < Array < Array < Int > > > ) -> Int :
            return literal ;
    """.trimIndent())

    @Test
    fun condition()  = parse("""
        func id ( id : Int ) -> Int : {
            if ( true ) {
                return literal ;
            }

            return id ;
        }

        func id ( id : Bool ) -> Array < Int > : {
            var id : Bool ;
            id = literal ;

            if ( false ) {
                id = literal ;
            } else {
                id = false ;
            }

            return id ;
        }
    """.trimIndent())

}
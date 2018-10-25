import grammar.rules.YOLANG
import org.junit.Test

class YolangParserTest : GrammarParsingTest(YOLANG) {

    @Test
    fun decl() = parse("""
        func Swap(array: Array<Int>, first: Int, second: Int) -> Array<Int>: {
            var temp: Int = array[first];
            array[first] = array[second];
            array[second] = temp;

            return array;
        }

        func BubbleSort(array: Array<Int>) -> Array<Int>: {
            var isSwapCompleted: Bool = true;
            var maxIndex: Int = GetMaxIndex(array);

            while (isSwapCompleted) {
                var current: Int = 0;

                while (current != maxIndex) {
                    var next: Int = current + 1;

                    if (array[current] > array[current]) {
                        array = Swap(array, current, next);
                        isSwapCompleted = true;
                    }

                    current = next;
                }
            }

            return array;
        }
        """.trimIndent())

    @Test
    fun loop() = parse("""
        func getElementFromArray(array: Array<Float>) -> Float: {
            var index: Int;
            index = getIndex(0);

            while()

            re
        }
        """.trimIndent())

    @Test
    fun arraysInArray() = parse("""
        func id(id: Int, id: Float) -> Int : {
            var id: Bool;
            return id;
        }

        func id (id: Array<Array<Array<Int>>>) -> Int :
            return literal;
    """.trimIndent())

    @Test
    fun condition() = parse("""
        func main() -> Int : {

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
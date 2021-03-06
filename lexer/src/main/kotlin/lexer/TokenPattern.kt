package lexer

import grammar.rules.Keyword
import grammar.rules.Operator
import grammar.rules.Type

internal object TokenPattern {

    private const val MAX_IDENTIFIER_SIZE = 120

    const val IDENTIFIER = "[_a-zA-Z][_a-zA-Z0-9]{0,${MAX_IDENTIFIER_SIZE - 1}}"

    const val NUMBER = "([1-9][0-9]*)|[0-9]"

    const val LETTER_OPERATOR = Keyword.Function +
            "|${Keyword.Condition}" +
            "|${Keyword.ConditionElse}" +
            "|${Keyword.Cycle}" +
            "|${Keyword.VariableDecl}" +
            "|${Keyword.Return}"

    const val TYPE_NAME = Type.Int +
            "|${Type.Float}" +
            "|${Type.Boolean}" +
            "|${Type.Array}"

    const val SYMBOL_OPERATOR = "->" +
            "|${Operator.Print}" +
            "|[${Operator.Or}]" +
            "|\\${Operator.And}" +
            "|\\${Operator.Equal}" +
            "|\\${Operator.NotEqual}" +
            "|\\${Operator.Plus}" +
            "|\\${Operator.Minus}" +
            "|\\${Operator.Mul}" +
            "|\\${Operator.Div}" +
            "|\\${Operator.Assign}" +
            "|\\${Operator.Less}" +
            "|\\${Operator.More}" +
            "|\\${Operator.Not}" +
            "|\\:" +
            "|\\," +
            "|\\;" +
            "|\\[" +
            "|\\]" +
            "|\\(" +
            "|\\)" +
            "|\\{" +
            "|\\}" +
            "|\\<" +
            "|\\>"

    const val COMMENT = "\\/\\/(.|\n^ \n)*"

    const val INVALID_IDENTIFIER = "[0-9]+${TokenPattern.IDENTIFIER}"

}
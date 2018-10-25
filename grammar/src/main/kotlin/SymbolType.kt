package grammar

enum class SymbolType {
    // Identifier
    IDENTIFIER,

    // Numbers
    NUMBER,

    // Keywords
    KEYWORD_FUNCTION,
    KEYWORD_IF,
    KEYWORD_ELSE,
    KEYWORD_WHILE,
    KEYWORD_VAR,
    KEYWORD_RETURN,

    // Operators
    SYMBOL_OPERATOR,

    // Types
    TYPE_INT,
    TYPE_FLOAT,
    TYPE_BOOLEAN,
    TYPE_ARRAY,

    // Other
    BOOLEAN_TRUE,
    BOOLEAN_FALSE,

    // Tools
    END_OF_INPUT,
    UNDEFINED
}

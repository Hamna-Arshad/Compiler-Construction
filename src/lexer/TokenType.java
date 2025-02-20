package lexer;

public enum TokenType {
    // Keywords
    NUM("num"),
    DEC("dec"),
    FLAG("Flag"),
    CHAR("char"),
    SHOW("Show"),
    EQUAL("equal"),
    
    // Operators
    PLUS("plus"),
    MINUS("minus"),
    MUL("mul"),
    DIV("div"),
    MOD("mod"),
    EXPONENT("^"),
    
    // Literals
    INTEGER_LITERAL,
    DECIMAL_LITERAL,
    BOOLEAN_LITERAL,
    CHAR_LITERAL,

    

    // Identifiers
    IDENTIFIER,
    
    // Comments
    SINGLE_LINE_COMMENT,
    MULTI_LINE_COMMENT,
    
    // Special
    EOF;

    private final String keyword;

    TokenType() {
        this.keyword = null;
    }

    TokenType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
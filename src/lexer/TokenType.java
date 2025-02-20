package src.lexer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TokenType {
    // Keywords
    KEYWORD,

    // Operators
    OPERATOR,

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

    // Keyword and Operator Sets
    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
            "num", "dec", "flag", "char", "show"
    ));

    private static final Set<String> OPERATORS = new HashSet<>(Arrays.asList(
            "plus", "minus", "mul", "div", "mod", "power","equal"
    ));

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

    public static TokenType getType(String token) {
        if (KEYWORDS.contains(token)) {
            return KEYWORD;
        }
        if (OPERATORS.contains(token)) {
            return OPERATOR;
        }
        return null; // Default case (handled elsewhere in your lexer)
    }
}

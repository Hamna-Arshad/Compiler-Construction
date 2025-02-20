package lexer;

import lexer.model.*;
import java.util.*;

public class LexicalAnalyzer
{
    private final ArrayList<Token> tokens;
    private final ArrayList<SymbolTableEntry> symbolTable;
    private final DFA dfa;
    private int line;
    private int column;

    public LexicalAnalyzer(String sourceCode) {
        this.tokens = new ArrayList<>();
        this.symbolTable = new ArrayList<>();
        this.line = 1;
        this.column = 1;

        // Create NFA and convert to DFA
        NFA nfa = new NFA();
        this.dfa = new DFA(nfa);

        // Print NFA and DFA transitions
        nfa.printTransitions();
        dfa.printTransitions();

        // Tokenize the source code
        tokenize(sourceCode);
    }

    private void tokenize(String sourceCode) {
        StringBuilder currentToken = new StringBuilder();
        boolean inSingleLineComment = false;
        boolean inMultiLineComment = false;

        for (int i = 0; i < sourceCode.length(); i++) {
            char c = sourceCode.charAt(i);

            // Handle newlines
            if (c == '\n') {
                line++;
                column = 1;
                if (inSingleLineComment) {
                    tokens.add(new Token(TokenType.SINGLE_LINE_COMMENT, currentToken.toString(), line, column));
                    currentToken.setLength(0);
                    inSingleLineComment = false;
                }
                continue;
            }

            // Handle comments
            if (!inSingleLineComment && !inMultiLineComment) {
                if (c == '*' && i + 1 < sourceCode.length() && sourceCode.charAt(i + 1) == '*') {
                    if (i + 2 < sourceCode.length() && sourceCode.charAt(i + 2) == '*') {
                        inMultiLineComment = true;
                        i += 2;
                        continue;
                    } else {
                        inSingleLineComment = true;
                        i++;
                        continue;
                    }
                }
            } else if (inMultiLineComment) {
                if (c == '*' && i + 2 < sourceCode.length() &&
                        sourceCode.charAt(i + 1) == '*' &&
                        sourceCode.charAt(i + 2) == '*') {
                    tokens.add(new Token(TokenType.MULTI_LINE_COMMENT, currentToken.toString(), line, column));
                    currentToken.setLength(0);
                    inMultiLineComment = false;
                    i += 2;
                    continue;
                }
            }

            // Add character to current token
            if (!Character.isWhitespace(c) || inSingleLineComment || inMultiLineComment) {
                currentToken.append(c);
            }
            column++;

            // Process token when we hit whitespace or end of line
            if ((Character.isWhitespace(c) || i == sourceCode.length() - 1) &&
                    !inSingleLineComment && !inMultiLineComment) {
                String tokenStr = currentToken.toString().trim();
                if (!tokenStr.isEmpty()) {
                    processToken(tokenStr);
                }
                currentToken.setLength(0);
            }
        }

        // Process any remaining token
        String tokenStr = currentToken.toString().trim();
        if (!tokenStr.isEmpty()) {
            processToken(tokenStr);
        }
    }

    private void processToken(String tokenStr) {
        TokenType type = null;

        // Check for keywords
        for (TokenType t : TokenType.values()) {
            if (t.getKeyword() != null && t.getKeyword().equals(tokenStr)) {
                type = t;
                tokens.add(new Token(type, tokenStr, line, column - tokenStr.length()));
                return;
            }
        }

        // Check if it's an identifier using DFA
        /*if (dfa.isAccepted(tokenStr)) {
            type = TokenType.IDENTIFIER;
            tokens.add(new Token(type, tokenStr, line, column - tokenStr.length()));
            addToSymbolTable(tokenStr, "IDENTIFIER", "global", null);
            return;
        }*/

        // Check for literals
        if (tokenStr.matches("\\d+")) {
            type = TokenType.INTEGER_LITERAL;
        } else if (tokenStr.matches("\\d*\\.\\d+")) {
            type = TokenType.DECIMAL_LITERAL;
        } else if (tokenStr.equals("true") || tokenStr.equals("false")) {
            type = TokenType.BOOLEAN_LITERAL;
        }

        if (type != null) {
            tokens.add(new Token(type, tokenStr, line, column - tokenStr.length()));
        } else {
            System.err.printf("Error: Invalid token '%s' at line %d, column %d%n",
                    tokenStr, line, column - tokenStr.length());
        }
    }

    private void addToSymbolTable(String name, String type, String scope, String value) {
        symbolTable.add(new SymbolTableEntry(name, type, scope, value));
    }

    public void printTokenTable() {
        System.out.println("\nToken Table:");
        System.out.println("Type\t\tValue\t\tLine\tColumn");
        System.out.println("-".repeat(50));
        for (Token token : tokens) {
            System.out.printf("%-15s\t%-15s\t%d\t%d%n",
                    token.getType(),
                    token.getValue(),
                    token.getLine(),
                    token.getColumn());
        }
    }

    public void printSymbolTable() {
        System.out.println("\nSymbol Table:");
        System.out.println("Name\t\tType\t\tScope\t\tValue");
        System.out.println("-".repeat(50));
        for (SymbolTableEntry entry : symbolTable) {
            System.out.printf("%-15s\t%-15s\t%-15s\t%s%n",
                    entry.getName(),
                    entry.getType(),
                    entry.getScope(),
                    entry.getValue());
        }
    }
}
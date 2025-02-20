package src.lexer;

import src.lexer.model.*;
import src.lexer.model.DFA;
import src.lexer.model.NFA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class LexicalAnalyzer
{
    private final ArrayList<Token> tokens;
    private final ArrayList<SymbolTableEntry> symbolTable;
    private final DFA dfa;
    private final NFA nfa;
    private int line;
    private int column;

    public LexicalAnalyzer(StringBuilder sourceCode) throws IOException {
        this.tokens = new ArrayList<>();
        this.symbolTable = new ArrayList<>();
        this.line = 1;
        this.column = 1;

        // Create NFA and convert to DFA
        this.nfa = new NFA();
        this.dfa = new DFA(nfa);

        // Print NFA and DFA transitions
        nfa.printTransitions();
        dfa.printTransitions();

        // Tokenize the source code
        tokenize(sourceCode);
    }

    private void tokenize(StringBuilder sourceCode) throws IOException {
        StringBuilder currentToken = new StringBuilder();
        String codee = String.valueOf(sourceCode);

        BufferedReader reader = new BufferedReader(new StringReader(codee));
        String currentLine;

        boolean inSingleLineComment = false;
        boolean inMultiLineComment = false;
        currentLine = reader.readLine();
        System.out.print(sourceCode.length());
        for (int i = 0; i < sourceCode.length(); i++)
        {
            //System.out.println("CURRRRRRRRRRRRRRRRRRR LINEEE "+ currentLine + "\n");
            char c = sourceCode.charAt(i);
            // newlines
            if (c == '\n') {
                currentLine = reader.readLine();
                line++;
                column = 1;
                if (inSingleLineComment) {

                    //tokens.add(new Token(TokenType.SINGLE_LINE_COMMENT, currentToken.toString(), line, column));
                    System.out.print("SINGLE LINE COMMENT " + currentToken.toString() + "\n");
                    currentToken.setLength(0);
                    inSingleLineComment = false;
                }
                continue;
            }

            // comments
            if (!inSingleLineComment && !inMultiLineComment) {
                if (c == '*' && i + 1 < sourceCode.length() && sourceCode.charAt(i + 1) == '*')
                {
                    if (i + 2 < sourceCode.length() && sourceCode.charAt(i + 2) == '*')
                    {
                        inMultiLineComment = true;
                        i += 2;
                        continue;
                    } else
                    {
                        inSingleLineComment = true;
                        i++;
                        continue;
                    }
                }
            } else if (inMultiLineComment) {
                if (c == '*' && i + 2 < sourceCode.length() &&
                        sourceCode.charAt(i + 1) == '*' &&
                        sourceCode.charAt(i + 2) == '*') {
                    //tokens.add(new Token(TokenType.MULTI_LINE_COMMENT, currentToken.toString(), line, column));
                    System.out.print("MULTI LINE COMMENT " + currentToken.toString() + "\n");
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
                    processToken(tokenStr, currentLine);
                }
                currentToken.setLength(0);
            }
        }

        // Process any remaining token
        String tokenStr = currentToken.toString().trim();
        if (!tokenStr.isEmpty()) {
            processToken(tokenStr,currentLine);
        }
    }
    private void processToken(String tokenStr, String Megastr)
    {
        TokenType type = null;

        // Check if it's an identifier
        TokenType tt = dfa.isAccepted(tokenStr);
        if (tt != null) {
            type = tt;
            tokens.add(new Token(type, tokenStr, line, column - tokenStr.length()));

            if (type == TokenType.IDENTIFIER) {
                boolean exists = false;

                for (SymbolTableEntry s : symbolTable) {
                    if (s.getName().equals(tokenStr)) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    System.out.print("Adding to Symbol Table: " + tokenStr + " -> " + Megastr + "\n");
                    if (Megastr != null && !Megastr.isEmpty()) {
                        addToSymbolTable(tokenStr, Megastr.split(" ")[0], "global", null);
                    }
                }
            }
            return;
        }

        // Check for literals
        if (tokenStr.matches("\\d+"))
        {
            type = TokenType.INTEGER_LITERAL;
        } else if (tokenStr.matches("\\d*\\.\\d+"))
        {
            type = TokenType.DECIMAL_LITERAL;
        } else if (tokenStr.equals("true") || tokenStr.equals("false"))
        {
            type = TokenType.BOOLEAN_LITERAL;
        }

        if (type != null) {
            tokens.add(new Token(type, tokenStr, line, column - tokenStr.length()));
        } else {
            System.out.printf("Error: Invalid token '%s' at line %d, column %d%n",
                    tokenStr, line, column - tokenStr.length());
        }
    }
/*
    private void processssToken(String tokenStr,String Megastr) {
        TokenType type = null;

        // Check for keywords
        for (TokenType t : TokenType.values())
        {
            if (t.getKeyword() != null && t.getKeyword().equals(tokenStr))
            {
                type = TokenType.getType(tokenStr);
                tokens.add(new Token(TokenType.KEYWORD, tokenStr, line, column - tokenStr.length()));
                System.out.print(tokens.getFirst());
                return;
            }
        }

        // Check if it's an identifier using DFA
        TokenType tt = dfa.isAccepted(tokenStr);
        if ( tt != null) {
            type = tt;
            boolean f = false;

            tokens.add(new Token(type, tokenStr, line, column - tokenStr.length()));
            if (type == TokenType.IDENTIFIER)
            {
                if (symbolTable.isEmpty())
                {
                    System.out.print("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss  " + Megastr + "\n");
                    addToSymbolTable(tokenStr, Megastr.split(" ")[0], "global", null);
                }
                for ( SymbolTableEntry s : symbolTable) {
                    System.out.print("HELOOOOO" + s.getName());
                    if (!s.getName().equals(tokenStr)) {
                        f = true;
                        break;
                    }
                }
                if(f){
                    System.out.print("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss  " + Megastr + "\n");
                    addToSymbolTable(tokenStr, Megastr.split(" ")[0], "global", null);
                    f = false;
                }
            }
            return;
        }

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
            System.out.printf("Error: Invalid token '%s' at line %d, column %d%n",
                    tokenStr, line, column - tokenStr.length());
        }
    }
*/
    private void addToSymbolTable(String name, String type, String scope, String value) {
        symbolTable.add(new SymbolTableEntry(name, type, scope, value));
    }

    public void printTokenTable() {
        System.out.println("\nToken Table:");
        System.out.println("Type\t\t\t\t\t\tValue");
        System.out.println("-".repeat(50));
        for (Token token : tokens) {
            System.out.printf("%-15s\t\t\t\t%-15s%n",
                    token.getType(),
                    token.getValue());
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

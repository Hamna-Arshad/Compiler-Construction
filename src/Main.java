



public class Main {
    public static void main(String[] args) {
        // Example source code
        String sourceCode = """
            dec  equal 10
           
            """;

        // Create lexical analyzer
        lexer.LexicalAnalyzer analyzer = new lexer.LexicalAnalyzer(sourceCode);
        
        // Print NFA and DFA transitions
        System.out.println("Lexical Analysis Results:");
        System.out.println("=========================");
        
        // Print token and symbol tables
        analyzer.printTokenTable();
        analyzer.printSymbolTable();
    }
}
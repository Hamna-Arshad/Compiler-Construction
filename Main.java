import compiler.automata.DFA;
import compiler.automata.NFA;
import compiler.regex.RegExParser;
import compiler.table.TransitionTable;
import compiler.utils.Constants;
import compiler.errorhandling.errorHandler;
import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
public class Main
{
    public static void main(String[] args)
    {
        errorHandler handler = new errorHandler();
        String sourceCode = "";

        // Read File
        try {
            File myObj = new File("./SrcCode.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                sourceCode += myReader.nextLine() + "\n";
                //System.out.println(sourceCode);
            }
            myReader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Tokenize src code
        tokenize(sourceCode);

        // Create lexical analyzer components
        RegExParser parser = new RegExParser();
        NFA nfa = parser.generateNFA();

        nfa.printNFA();
        DFA dfa = new DFA(nfa);
        TransitionTable table = new TransitionTable(dfa);
        
        // Display transition table
        table.display();


    }
    
    private static void tokenize(String sourceCode) {
        System.out.println("\nTokens\n");
        String[] lines = sourceCode.split("\n");
        for (int lineNum = 0; lineNum < lines.length; lineNum++) {
            String line = lines[lineNum].trim();
            if (line.isEmpty()) continue;
            
            // Process each line
            processLine(line, lineNum + 1);
        }
    }
    
    private static void processLine(String line, int lineNum) {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (Character.isWhitespace(c)) {
                processToken(token.toString(), lineNum);
                token = new StringBuilder();
            } else {
                token.append(c);
            }
        }
        
        // Process last token if exists
        if (!token.isEmpty()) {
            processToken(token.toString(), lineNum);
        }
    }
    
    private static void processToken(String token, int lineNum)
    {
        if (token.isEmpty()) return;
        
        if (Constants.KEYWORDS.contains(token)) {
            System.out.println("Keyword: " + token);
        } else if (token.matches(Constants.NUMBER_PATTERN)) {
            System.out.println("Number: " + token);
        } else if (token.matches(Constants.IDENTIFIER_PATTERN)) {
            System.out.println("Identifier: " + token);
        } else //System.out.println("Error at line " + lineNum + ": Invalid token '" + token + "'");
            if (Constants.OPERATORS.contains(token)) {
            System.out.println("Operator: " + token);
        } else errorHandler.handleError(lineNum, token);
    }
}
/*
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
        StringBuilder sourceCode = new StringBuilder();
        errorHandler handler = new errorHandler();

        // Read File
        try {
            File myObj = new File("./SrcCode.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                sourceCode.append(myReader.nextLine()).append("\n");
                //System.out.println(sourceCode);
            }
            myReader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Create lexical analyzer components
        RegExParser parser = new RegExParser();
        NFA nfa = parser.generateNFA();

        DFA dfa = new DFA(nfa);
        TransitionTable table = new TransitionTable(dfa,nfa);

        // Tokenize src code
        tokenize(sourceCode.toString());

        // Display transition table
        table.displayNFA();
        table.displayDFA();


    }
    
    private static void tokenize(String sourceCode) {
        System.out.println("\n\t\tToken Table");
        System.out.printf("%-15s %-15s%n", "Token Type", "Token Value");
        System.out.println("------------------------------------");

        StringBuilder Errors = new StringBuilder();

        String[] lines = sourceCode.split("\n");

        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            
            // Process each line
            Errors.append(processLine(line, i + 1));
        }
        // Print Errors in the end
        System.out.println("\n\t\t\tErrors\n" + "------------------------------------\n" + Errors + "------------------------------------\n");


    }
    
    private static String processLine(String line, int lineNum) {
        StringBuilder token = new StringBuilder();
        StringBuilder Errors= new StringBuilder();

        for (int i = 0; i < line.length(); i++)
        {
            char c = line.charAt(i);
            
            if (Character.isWhitespace(c)) {
                Errors.append(processToken(token.toString(), lineNum));
                token = new StringBuilder();
            } else {
                token.append(c);
            }
        }
        
        // Process last token if exists
        if (!token.isEmpty()) {
            Errors.append(processToken(token.toString(), lineNum));
        }
        return Errors.toString();

    }
    
    private static StringBuilder processToken(String token, int lineNum)
    {
        StringBuilder Errors= new StringBuilder();

        if (token.isEmpty())
            return Errors;

        if (Constants.KEYWORDS.contains(token)) {
            System.out.printf("%-15s %-15s%n", " Keyword", token);
        } else if (token.matches(Constants.NUMBER_PATTERN)) {
            System.out.printf("%-15s %-15s%n", " Number", token);
        } else if (token.matches(Constants.IDENTIFIER_PATTERN)) {
            System.out.printf("%-15s %-15s%n", " Identifier", token);
        } else if (Constants.OPERATORS.contains(token)) {
            System.out.printf("%-15s %-15s%n", " Operator", token);
        } else {
            Errors.append(" Line ").append(lineNum).append(": Invalid token '").append(token).append("'\n");
        }
        return Errors;
    }
}
*/
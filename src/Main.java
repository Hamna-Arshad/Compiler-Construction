import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        StringBuilder SrcCode = new StringBuilder();
        // Read File
        try {
            File myObj = new File("./SrcCode.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                SrcCode.append(myReader.nextLine()).append("\n\r");
                //System.out.println(SrcCode);
            }
            myReader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Create lexical analyzer
        lexer.LexicalAnalyzer analyzer = new lexer.LexicalAnalyzer(SrcCode);
        
        // Print NFA and DFA transitions
        System.out.println("Lexical Analysis Results:");
        System.out.println("=========================");
        
        // Print token and symbol tables
        analyzer.printTokenTable();
        analyzer.printSymbolTable();
    }
}

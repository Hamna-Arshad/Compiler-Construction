package compiler.errorhandling;

public class errorHandler {

 public static void handleError(int lineNum, String token){
     System.out.println("Error at line " + lineNum + ": Invalid token '" + token + "'");
 }
}

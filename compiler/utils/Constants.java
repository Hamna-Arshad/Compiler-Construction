package compiler.utils;

import compiler.errorhandling.errorHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
public class Constants {

    // Keywords
    public static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
            "num", "dec", "bool", "letter", "sentence", "show" ));
    
    // Operators
    public static final Set<String> OPERATORS = new HashSet<>(Arrays.asList(
        "plus", "minus", "mul", "div", "mod", "power", "equal"));
    
    // Regex Patterns
    public static final String IDENTIFIER_PATTERN = "[a-z][a-z0-9]*";
    public static final String NUMBER_PATTERN = "\\d+(\\.\\d{1,5})?";


    
    // State Types
    public static final String ACCEPT_STATE = "ACCEPT";
    public static final String REJECT_STATE = "REJECT";
    public static final String START_STATE = "START";
}

// if something is not a Keyword or a string then it is an identifier


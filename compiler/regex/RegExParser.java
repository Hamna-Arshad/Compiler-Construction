package compiler.regex;
import compiler.automata.NFA;
import compiler.automata.State;
import compiler.utils.Constants;


public class RegExParser
{
    public NFA generateNFA()
    {
        NFA nfa = new NFA();

        // Create states for different token types
        State startState = new State(Constants.START_STATE, false);
        State identifierState = new State("IDENTIFIER", true);
        State numberState = new State("NUMBER", true);
        State operatorState = new State("OPERATOR", true);
        State keywordState = new State("KEYWORDS",true);
        
        nfa.setStartState(startState);
        nfa.addState(identifierState);
        nfa.addState(numberState);
        nfa.addState(operatorState);
        nfa.addState(keywordState);
        
        // Add transitions for identifiers (a-z)
        for (char c = 'a'; c <= 'z'; c++) {
            startState.addTransition(c, identifierState);
            identifierState.addTransition(c, identifierState);
        }
        
        // Add transitions for numbers (0-9)
        for (char c = '0'; c <= '9'; c++) {
            startState.addTransition(c, numberState);
            numberState.addTransition(c, numberState);
        }

        // Add transitions for operators
        for (String op : Constants.KEYWORDS) {
            //System.out.println("Heree "+op);
            startState.addTransition(op.charAt(0), operatorState);
        }

        // Add transitions for operators
        for (String op : Constants.OPERATORS) {
            //System.out.println("Heree "+op);
            startState.addTransition(op.charAt(0), operatorState);
        }
        
        return nfa;
    }
}
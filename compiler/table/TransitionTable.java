
package compiler.table;

import compiler.automata.DFA;
import compiler.automata.State;
import java.util.Map;

public class TransitionTable {
    private DFA dfa;
    
    public TransitionTable(DFA dfa) {
        this.dfa = dfa;
    }
    
    public void display() {
        System.out.println("\nTransition Table:");
        System.out.println("----------------");
        
        // Print header
        System.out.print("State\t");
        for (char c = 'a'; c <= 'z'; c++) {
            System.out.print(c + "\t");
        }
        for (char c = '0'; c <= '9'; c++) {
            System.out.print(c + "\t");
        }
        System.out.println("Accepting?");
        
        // Print transitions for each state
        for (State state : dfa.getStates()) {
            System.out.print(state.getName() + "\t");
            
            Map<Character, State> transitions = state.getTransitions();
            for (char c = 'a'; c <= 'z'; c++) {
                State nextState = transitions.get(c);
                System.out.print((nextState != null ? nextState.getName() : "-") + "\t");
            }
            for (char c = '0'; c <= '9'; c++) {
                State nextState = transitions.get(c);
                System.out.print((nextState != null ? nextState.getName() : "-") + "\t");
            }
            
            System.out.println(state.isAccepting() ? "Yes" : "No");
        }
    }
}

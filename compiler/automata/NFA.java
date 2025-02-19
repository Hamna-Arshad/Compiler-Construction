//replace later
package compiler.automata;

import java.util.*;

public class NFA {
    private State startState;
    private Set<State> states;
    private Set<State> acceptStates;
    
    public NFA() {
        this.states = new HashSet<>();
        this.acceptStates = new HashSet<>();
    }
    
    public void setStartState(State state) {
        this.startState = state;
        states.add(state);
    }
    
    public void addState(State state) {
        states.add(state);
        if (state.isAccepting()) {
            acceptStates.add(state);
        }
    }
    
    public Set<State> getStates() {
        return states;
    }
    
    public State getStartState() {
        return startState;
    }
    
    public Set<State> getAcceptStates() {
        return acceptStates;
    }
    
    public Set<State> getNextStates(State currentState, char input) {
        Set<State> nextStates = new HashSet<>();

        State nextState = currentState.getNextState(input);
        if (nextState != null) {
            nextStates.add(nextState);
        }
        return nextStates;
    }
    public void printNFA() {
        // Collect all input symbols from the states
        Set<Character> inputSymbols = new HashSet<>();
        for (State state : states) {
            inputSymbols.addAll(state.getTransitions().keySet());
        }

        // Sort input symbols for consistent ordering
        List<Character> sortedSymbols = new ArrayList<>(inputSymbols);
        Collections.sort(sortedSymbols);

        // Print header row
        System.out.print("States\t");
        System.out.print("ε\t"); // Null (epsilon) transitions
        for (char symbol : sortedSymbols) {
            System.out.print(symbol + "\t");
        }
        System.out.println();

        // Print transition table
        for (State state : states) {
            System.out.print(state.getName() + "\t");

            // Print epsilon transitions (if any)
            Set<State> epsilonTransitions = state.getNullTransitions();
            System.out.print(formatStates(epsilonTransitions) + "\t");

            // Print transitions for each input symbol
            for (char symbol : sortedSymbols) {
                Set<State> nextStates = state.getAllNextStates(symbol);
                System.out.print(formatStates(nextStates) + "\t");
            }
            System.out.println();
        }

        // Print accept states
        System.out.println("\nAccept States: " + acceptStates);
    }

    // Helper method to format states
    private String formatStates(Set<State> states) {
        if (states == null || states.isEmpty()) {
            return "-"; // No transition
        }
        StringBuilder sb = new StringBuilder();
        for (State state : states) {
            sb.append(state.getName()).append(",");
        }
        return sb.substring(0, sb.length() - 1); // Remove last comma
    }


}

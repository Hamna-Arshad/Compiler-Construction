package compiler.automata;

import java.util.*;

public class State {
    private String name;
    private boolean isAccepting;
    private Map<Character, Set<State>> transitions; // Supports multiple transitions per input

    public State(String name, boolean isAccepting) {
        this.name = name;
        this.isAccepting = isAccepting;
        this.transitions = new HashMap<>();
    }

    // Add a transition (allows multiple states per symbol)
    public void addTransition(char input, State nextState) {
        transitions.putIfAbsent(input, new HashSet<>());
        transitions.get(input).add(nextState);
    }

    // Get a single next state (for DFA behavior)
    public State getNextState(char input) {
        Set<State> nextStates = transitions.get(input);
        if (nextStates != null && nextStates.size() == 1) {
            return nextStates.iterator().next(); // Return the only state if deterministic
        }
        return null; // Return null if no transition or multiple states exist (NFA case)
    }

    // Get all possible next states (for NFA)
    public Set<State> getAllNextStates(char symbol) {
        return transitions.getOrDefault(symbol, Collections.emptySet());
    }

    // Get epsilon (null) transitions
    public Set<State> getNullTransitions() {
        return transitions.getOrDefault('\0', Collections.emptySet());
    }

    public String getName() {
        return name;
    }

    public boolean isAccepting() {
        return isAccepting;
    }

    // Modified to avoid duplicate methods
    public Map<Character, Set<State>> getTransitions() {
        return transitions;
    }

    // Specific method for DFA transitions (assumes only one next state per symbol)
    public Map<Character, State> getDfaTransitions() {
        Map<Character, State> dfaTransitions = new HashMap<>();
        for (Map.Entry<Character, Set<State>> entry : transitions.entrySet()) {
            if (entry.getValue().size() == 1) {
                dfaTransitions.put(entry.getKey(), entry.getValue().iterator().next());
            }
        }
        return dfaTransitions;
    }

    @Override
    public String toString() {
        return "State{name='" + name + "', accepting=" + isAccepting + "}";
    }
}

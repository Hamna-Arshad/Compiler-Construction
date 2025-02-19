package compiler.automata;

import java.util.*;

public class DFA {
    private State startState;
    private Set<State> states;
    private Set<Character> alphabet;
    
    public DFA(NFA nfa) {
        this.states = new HashSet<>();
        this.alphabet = new HashSet<>();
        convertFromNFA(nfa);
    }
    
    private void convertFromNFA(NFA nfa) {
        // Subset construction algorithm
        Map<Set<State>, State> dfaStates = new HashMap<>();
        Queue<Set<State>> unprocessedStates = new LinkedList<>();
        
        // Start with NFA's start state
        Set<State> initialSet = new HashSet<>();
        initialSet.add(nfa.getStartState());
        
        // Create DFA's start state
        State dfaStart = new State("0", nfa.getStartState().isAccepting());
        dfaStates.put(initialSet, dfaStart);
        unprocessedStates.add(initialSet);
        this.startState = dfaStart;
        this.states.add(dfaStart);
        
        // Process all states
        while (!unprocessedStates.isEmpty()) {
            Set<State> currentSet = unprocessedStates.poll();
            State currentDFAState = dfaStates.get(currentSet);
            
            // For each input symbol
            for (char input : getAlphabet(nfa)) {
                Set<State> nextSet = new HashSet<>();
                
                // Get all possible next states from NFA
                for (State nfaState : currentSet) {
                    nextSet.addAll(nfa.getNextStates(nfaState, input));
                }
                
                if (!nextSet.isEmpty()) {
                    // Create new DFA state if needed
                    if (!dfaStates.containsKey(nextSet)) {
                        boolean isAccepting = nextSet.stream().anyMatch(State::isAccepting);
                        State newState = new State(Integer.toString(dfaStates.size()), isAccepting);
                        dfaStates.put(nextSet, newState);
                        unprocessedStates.add(nextSet);
                        this.states.add(newState);
                    }
                    
                    // Add transition
                    currentDFAState.addTransition(input, dfaStates.get(nextSet));
                }
            }
        }
    }
    
    private Set<Character> getAlphabet(NFA nfa) {
        if (alphabet.isEmpty()) {
            for (State state : nfa.getStates()) {
                alphabet.addAll(state.getTransitions().keySet());
            }
        }
        return  alphabet;
    }
    //comment
    public State getStartState() {
        return startState;
    }
    
    public Set<State> getStates() {
        return states;
    }
}

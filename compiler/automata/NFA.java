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
    public void printNFA()
    {
        System.out.println("Printing NFA: " + (startState != null ? startState : "None"));

        System.out.println("States:");
        for (State state : states) {
            System.out.println("  " + state);
        }

        System.out.println("Accept States:");
        for (State state : acceptStates) {
            System.out.println("  " + state);
        }
    }

}

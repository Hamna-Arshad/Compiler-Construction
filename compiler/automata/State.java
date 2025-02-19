package compiler.automata;

import java.util.HashMap;
import java.util.Map;

public class State {
    private String name;
    private boolean isAccepting;
    private Map<Character, State> transitions;
    
    public State(String name, boolean isAccepting) {
        this.name = name;
        this.isAccepting = isAccepting;
        this.transitions = new HashMap<>();
    }
    
    public void addTransition(char input, State nextState) {
        transitions.put(input, nextState);
    }
    
    public State getNextState(char input) {
        return transitions.get(input);
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isAccepting() {
        return isAccepting;
    }
    
    public Map<Character, State> getTransitions() {
        return transitions;
    }
    @Override
    public String toString() {
        return "State{name='" + name + "', accepting=" + isAccepting + "}";
    }

}

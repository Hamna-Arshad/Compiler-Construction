package src.lexer.model;

import java.util.*;

public class State {
    private int id;
    private String name;
    private boolean isAccepting;
    private ArrayList<Transition> transitions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State(int id) {
        this.id = id;
        this.isAccepting = false;
        this.transitions = new ArrayList<>();
    }

    public void addTransition(char symbol, State target) {

        transitions.add(new Transition(symbol, target));
    }

    public ArrayList<State> getTransitions(char symbol) {
        ArrayList<State> targets = new ArrayList<>();
        for (Transition t : transitions) {
            if (t.symbol == symbol) {
                targets.add(t.target);
            }
        }
        return targets;
    }

    public ArrayList<Transition> getAllTransitions() {
        return transitions;
    }

    public void setAccepting(boolean accepting) {
        isAccepting = accepting;
    }

    public boolean isAccepting() {
        return isAccepting;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "q" + id + (isAccepting ? "(accepting)" : "");
    }

    private static class Transition {
        char symbol;
        State target;

        Transition(char symbol, State target) {
            this.symbol = symbol;
            this.target = target;
        }
    }
}

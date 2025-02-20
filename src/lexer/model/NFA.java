package src.lexer.model;

import java.util.*;

public class NFA {
    private State startState;
    private ArrayList<State> states;
    private ArrayList<Character> alphabet;
    private int stateCounter;

    public NFA() {
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.stateCounter = 0;
        initializeNFA();
    }

    private State createState() {
        State state = new State(stateCounter++);
        states.add(state);
        return state;
    }

    private void initializeNFA() {
        // Initialize alphabet
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            alphabet.add(c);
        }
        alphabet.add('.');
        alphabet.add('*');
        alphabet.add('+');
        alphabet.add('-');
        alphabet.add('/');
        alphabet.add('%');
        alphabet.add('^');

        // Create start state
        startState = createState();

        // Create states for identifiers (lowercase letters)
        State identifierState = createState();
        State identifierFinal = createState();
        identifierFinal.setAccepting(true);

        // Transitions for identifiers
        for (char c = 'a'; c <= 'z'; c++) {
            startState.addTransition(c, identifierState);
            identifierState.addTransition(c, identifierState);
        }
        identifierState.addTransition('\0', identifierFinal);  // epsilon transition

        // Create states for numbers
        State numberState = createState();
        State numberFinal = createState();
        State decimalState = createState();
        State decimalFinal = createState();
        numberFinal.setAccepting(true);
        decimalFinal.setAccepting(true);

        // Transitions for numbers
        for (char c = '0'; c <= '9'; c++) {
            startState.addTransition(c, numberState);
            numberState.addTransition(c, numberState);
            decimalState.addTransition(c, decimalState);
        }
        numberState.addTransition('\0', numberFinal);  // epsilon transition
        numberState.addTransition('.', decimalState);
        decimalState.addTransition('\0', decimalFinal);  // epsilon transition

        // Create states for comments
        State commentStart = createState();
        State singleCommentState = createState();
        State multiCommentState = createState();
        State singleCommentFinal = createState();
        State multiCommentFinal = createState();
        singleCommentFinal.setAccepting(true);
        multiCommentFinal.setAccepting(true);

        // Transitions for comments
        startState.addTransition('*', commentStart);
        commentStart.addTransition('*', singleCommentState);
        commentStart.addTransition('*', multiCommentState);
        singleCommentState.addTransition('*', singleCommentFinal);
        multiCommentState.addTransition('*', multiCommentFinal);
    }

    public ArrayList<State> getEpsilonClosure(ArrayList<State> states) {
        Stack<State> stack = new Stack<>();
        ArrayList<State> closure = new ArrayList<>(states);

        for (State state : states) {
            stack.push(state);
        }

        while (!stack.isEmpty()) {
            State current = stack.pop();
            ArrayList<State> epsilonTransitions = current.getTransitions('\0');

            for (State next : epsilonTransitions) {
                if (!closure.contains(next)) {
                    closure.add(next);
                    stack.push(next);
                }
            }
        }
        return closure;
    }

    public State getStartState() {
        return startState;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public ArrayList<Character> getAlphabet() {
        return alphabet;
    }

    public void printTransitions() {
        System.out.println("\nNFA States and Transitions:");
        System.out.println("Total States: " + states.size());
        System.out.println("\nTransition Table:");
        System.out.println("State\t\tInput\t\tNext State(s)");
        System.out.println("-".repeat(50));

        for (State state : states) {
            boolean firstTransition = true;
            for (char symbol : alphabet) {
                ArrayList<State> targets = state.getTransitions(symbol);
                if (!targets.isEmpty()) {
                    if (firstTransition) {
                        System.out.printf("q%-8d\t%-8s\t%s%n",
                                state.getId(),
                                symbol == '\0' ? "ε" : symbol,
                                targets);
                        firstTransition = false;
                    } else {
                        System.out.printf("        \t%-8s\t%s%n",
                                symbol == '\0' ? "ε" : symbol,
                                targets);
                    }
                }
            }
        }
    }
}

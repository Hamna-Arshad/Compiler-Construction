package lexer.model;

import java.util.*;

public class DFA {
    private ArrayList<ArrayList<State>> dfaStates;
    private ArrayList<State> startState;
    private ArrayList<Transition> transitions;
    private ArrayList<ArrayList<State>> acceptingStates;
    private ArrayList<Character> alphabet;

    public DFA(NFA nfa) {
        this.dfaStates = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.acceptingStates = new ArrayList<>();
        this.alphabet = new ArrayList<>(nfa.getAlphabet());

        convertFromNFA(nfa);
    }

    private void convertFromNFA(NFA nfa) {
        // Start with epsilon closure of NFA's start state
        ArrayList<State> initialState = new ArrayList<>();
        initialState.add(nfa.getStartState());
        startState = nfa.getEpsilonClosure(initialState);

        // Initialize processing queue
        Queue<ArrayList<State>> processingQueue = new LinkedList<>();
        processingQueue.offer(startState);
        dfaStates.add(startState);

        while (!processingQueue.isEmpty()) {
            ArrayList<State> currentState = processingQueue.poll();

            // Process each input symbol
            for (char symbol : alphabet) {
                ArrayList<State> nextStates = new ArrayList<>();

                // Get all transitions for current symbol
                for (State state : currentState) {
                    nextStates.addAll(state.getTransitions(symbol));
                }

                // Get epsilon closure
                ArrayList<State> closure = nfa.getEpsilonClosure(nextStates);

                // Add transition
                if (!closure.isEmpty()) {
                    transitions.add(new Transition(currentState, symbol, closure));

                    // Add new state if not seen before
                    if (!dfaStates.contains(closure)) {
                        dfaStates.add(closure);
                        processingQueue.offer(closure);

                        // Check if accepting state
                        for (State s : closure) {
                            if (s.isAccepting()) {
                                acceptingStates.add(closure);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isAccepted(String input) {
        ArrayList<State> currentState = startState;

        for (char c : input.toCharArray()) {
            boolean transitionFound = false;
            ArrayList<State> nextState = null;

            for (Transition t : transitions) {
                if (t.from.equals(currentState) && t.symbol == c) {
                    nextState = t.to;
                    transitionFound = true;
                    break;
                }
            }

            if (!transitionFound) {
                return false;
            }
            currentState = nextState;
        }

        return acceptingStates.contains(currentState);
    }

    public void printTransitions() {
        System.out.println("\nDFA States and Transitions:");
        System.out.println("Total States: " + dfaStates.size());
        System.out.println("\nTransition Table:");
        System.out.println("State\t\tInput\t\tNext State");
        System.out.println("-".repeat(50));

        for (ArrayList<State> state : dfaStates) {
            boolean firstTransition = true;
            for (Transition t : transitions) {
                if (t.from.equals(state)) {
                    if (firstTransition) {
                        System.out.printf("%-8s\t%-8s\t%s%n",
                                stateToString(state),
                                t.symbol,
                                stateToString(t.to));
                        firstTransition = false;
                    } else {
                        System.out.printf("        \t%-8s\t%s%n",
                                t.symbol,
                                stateToString(t.to));
                    }
                }
            }
        }
    }

    private String stateToString(ArrayList<State> states) {
        StringBuilder sb = new StringBuilder("{");
        for (State state : states) {
            sb.append("q").append(state.getId()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    private static class Transition {
        ArrayList<State> from;
        char symbol;
        ArrayList<State> to;

        Transition(ArrayList<State> from, char symbol, ArrayList<State> to) {
            this.from = from;
            this.symbol = symbol;
            this.to = to;
        }
    }
}


package compiler.table;

import compiler.automata.DFA;
import compiler.automata.NFA;
import compiler.automata.State;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TransitionTable {
    private final DFA dfa;
    private final NFA nfa;

    public TransitionTable(DFA dfa, NFA nfa) {
        this.dfa = dfa;
        this.nfa = nfa;
    }

    public void displayDFA() {
        if (dfa == null) {
            System.out.println("No DFA available.");
            return;
        }

        System.out.println("\nDFA Transition Table:");
        System.out.println("----------------");

        // Print header
        System.out.print("State  ||\t");
        for (char c = 'a'; c <= 'z'; c++) {
            System.out.print(c + "\t");
        }
        for (char c = '0'; c <= '9'; c++) {
            System.out.print(c + "\t");
        }
        System.out.print(".  ");
        System.out.println("||  Accepting");
        System.out.println("============================================================================================================================================================================");

        // sorting the state list
        List<State> stateList = new ArrayList<>(dfa.getStates());
        stateList.sort(Comparator.comparingInt(state -> Integer.parseInt(state.getName())));


        // Print transitions for each state
        for (State state : dfa.getStates()) {
            System.out.print(state.getName() + "  ||" + "\t");

            Map<Character, State> transitions = state.getDfaTransitions();
            for (char c = 'a'; c <= 'z'; c++) {
                State nextState = transitions.get(c);
                System.out.print((nextState != null ? nextState.getName() : "-") + "\t");
            }
            for (char c = '0'; c <= '9'; c++) {
                State nextState = transitions.get(c);
                System.out.print((nextState != null ? nextState.getName() : "-") + "\t");
            }
            char c = '.';
            System.out.print((transitions.get(c) != null ? transitions.get(c).getName() : "-") + "  ");
            System.out.println(state.isAccepting() ? "||  Yes" : "||  No");
        }
    }

    public void displayNFA() {
        if (nfa == null) {
            System.out.println("No NFA available.");
            return;
        }

        System.out.println("\nNFA Transition Table:");

        // Define meaningful state names
        Map<Integer, String> stateLabels = Map.of(
                0, "Start",
                1, "Number",
                2, "Keyword",
                3, "Operator",
                4, "Identifier"
        );

        // Assign numerical IDs to states
        int stateIndex = 0;
        Map<State, Integer> stateNumbers = new HashMap<>();
        for (State state : nfa.getStates()) {
            stateNumbers.put(state, stateIndex++);
        }

        // Print state mappings
        System.out.println("\nState Mappings:");
        for (Map.Entry<State, Integer> entry : stateNumbers.entrySet()) {
            int stateNum = entry.getValue();
            String label = stateLabels.getOrDefault(stateNum, "State " + stateNum);
            System.out.println(stateNum + " -> " + label);
        }

        System.out.println("\n----------------------------------------------------------------------------");

        // Print header row
        System.out.print("State\tε\t\t\t");
        for (char c = 'a'; c <= 'z'; c++) {
            System.out.print(c + "\t\t\t");
        }
        for (char c = '0'; c <= '9'; c++) {
            System.out.print(c + "\t\t\t");
        }
        System.out.println("Accepting?");

        System.out.println("----------------------------------------------------------------------------");

        // Print transitions for each state
        for (State state : nfa.getStates()) {
            int stateNum = stateNumbers.get(state);
            System.out.print("  " + stateNum + "  \t");

            // Print epsilon (null) transitions
            System.out.print(formatStateSet(state.getNullTransitions(), stateNumbers) + "\t\t");

            // Print transitions for 'a' to 'z'
            for (char c = 'a'; c <= 'z'; c++) {
                System.out.print(formatStateSet(state.getAllNextStates(c), stateNumbers) + "\t\t");
            }

            // Print transitions for '0' to '9'
            for (char c = '0'; c <= '9'; c++) {
                System.out.print(formatStateSet(state.getAllNextStates(c), stateNumbers) + "\t\t");
            }

            // Indicate if the state is accepting
            System.out.println(state.isAccepting() ? "Yes" : "No");
        }
    }


    private String formatStateSet(Set<State> states, Map<State, Integer> stateNumbers) {
        if (states == null || states.isEmpty()) return String.format("%-4s", "-");

        // Format states with numeric labels
        String formattedStates = states.stream()
                .map(stateNumbers::get)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return String.format("%-4s", formattedStates);  // Ensures a fixed 8-character width
    }

}

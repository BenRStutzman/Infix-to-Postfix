import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

/**
* This class converts a string in infix notation (e.g. "( 3 + 4 ) * 5") to
*  postfix notation (e.g. "3 4 + 5 *"). It does this by going through the infix
*  from left to right and copying symbols to the output, or pushing them to a
* stack until a lower-precedence operation is found.
*/
public class InfixToPostfix {
   
    private static int precedence(String op) {
        // Determines precedence of operations, or returns 0 if op is unknown
        switch (op) {
            case "+": return 1;
            case "-": return 1;
            case "*": return 2;
            case "/": return 2;
            case "%": return 2;
            case "^": return 3;
            default: return 0;
        }
    }
    
    private static Queue Postfix(String[] infix) {
        // Converts an array of strings in infix to a postfix queue and returns it
        // Make a stack for storing operations for later:
        Stack stack = new Stack <String> ();
        Queue output = new LinkedList <String> ();
        for (String symbol : infix) {
            if (symbol.matches("\\d+")) {
                output.add(symbol); // Copy numbers to output
            } else if (symbol.length() == 1) {
                if (symbol.equals("(")) {
                    stack.push(symbol); // Copy left parens to output
                } else if (symbol.equals(")")) {
                    // Output all operators since the last left paren:
                    while (!stack.isEmpty()) {
                        if (stack.peek().equals("(")) break;
                        output.add(stack.pop());
                    }
                    if (!stack.isEmpty()) {
                        if (stack.pop().equals("(")) {
                            continue;
                        }
                    }
                    // No left paren in the stack:
                    System.out.printf("Invalid input: ");
                    System.out.printf("Parentheses must match.");
                    return null;
                } else { // Single-character operator
                    int prec = precedence(symbol);
                    if (prec == 0) { // Unknown operator:
                        System.out.printf("Invalid input: %s\n", symbol);
                        System.out.println("The only valid symbols are: " +
                                           "+ - * / % ^ ( )");
                        return null;
                    }
                    // Pop and enqueue all ops in the stack
                    // of equal or greater precedence:
                    while (!stack.isEmpty()) {
                        if (prec > precedence((String) stack.peek())
                            | stack.peek().equals("(")) break;
                        output.add(stack.pop());
                    }
                    stack.push(symbol); // Add the current op to the stack
                }
            } else { // Multi-character input that isn't a number
                System.out.printf("Invalid input: %s\n", symbol);
                System.out.println("Please enter only space-separated " +
                                   "whole numbers, operations, and " +
                                   "parentheses.");
                return null;
            }
        }
        // Copy all the remaining operations to output:
        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
        return output;
    }
        
    public static void main(String[] args) {
        // Takes a string in infix and prints a string in postfix
        if (args.length > 0) {
            for (String input : args) { // Can convert multiple infix strings
                String[] infix = input.split("\\s+");
                // Convert infix array to a postfix queue
                Queue postfix = Postfix(infix);
                if (postfix != null) {
                    // Print out all the symbols in output
                    // in the order they were added:
                    while (postfix.size() > 1) {
                        System.out.print(postfix.remove() + " ");
                    }
                    //Last one doesn't have a space after it:
                    System.out.println(postfix.remove());
                }
            }
        } else {
            System.out.println("No input given.");
            System.out.println("Please enter a string in infix notation, with " +
                               "space-separated whole numbers, operations, " +
                               "and parentheses.");
            System.out.println("Ex: \"( 3 + 4 ) * 5\"");
        }
    }
    
}
    
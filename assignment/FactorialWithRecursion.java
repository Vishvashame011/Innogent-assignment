package assignment;

import java.math.BigInteger;
import java.util.Scanner;

public class FactorialWithRecursion {

    // Recursive method to calculate factorial using BigInteger
    // Method code itself is stored in the Method Area.
    // Each call creates a new stack frame.
    public static BigInteger factorial(BigInteger n) {
        // 'n' is a reference variable stored in the current stack frame
        // The BigInteger object it points to lives in the Heap.

        // Base case: if n is 0 or 1, return 1 (0! = 1, 1! = 1)
        if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) {
            return BigInteger.ONE; // BigInteger.ONE is a constant object from Heap loaded via Method Area
        }

        // Recursive case: n! = n * (n-1)!
        // factorial(n.subtract(BigInteger.ONE)) creates a new stack frame for the recursive call
        // n.subtract(BigInteger.ONE)  returns a new BigInteger object on the Heap
        return n.multiply(factorial(n.subtract(BigInteger.ONE)));
        // multiply() also creates a new BigInteger object on the Heap and returns its reference.
    }

    public static void main(String[] args) {
        // main() method code is in the Method Area, its execution frame is in the Thread Stack.

        System.out.print("Enter a number for factorial: ");
        Scanner sc = new Scanner(System.in);
        // 'sc' is a reference variable on the Stack pointing to a Scanner object in the Heap.

        int input = sc.nextInt();
        // 'input' is a primitive local variable stored in the Stack Frame of main()

        if (input < 0) {
            System.out.println("Factorial is not defined for negative numbers.");
        } else {
            // BigInteger.valueOf(input) returns a BigInteger object on the Heap
            // 'n' reference variable is stored on Stack
            BigInteger n = BigInteger.valueOf(input);

            // factorial(n) pushes multiple stack frames (one per recursive call) on the Thread Stack
            System.out.println("Factorial of " + input + " is : " + factorial(n));
        }
    }
}

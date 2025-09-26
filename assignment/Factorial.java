package assignment;

import java.math.BigInteger;
import java.util.Scanner;

public class Factorial {

    // Method now supports BigInteger and handles negative input
    public static BigInteger factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial not defined for negative numbers");
        }

        BigInteger fact = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }

        return fact;
    }

    public static void main(String[] args) {
        System.out.print("Enter the number for getting factorial of that: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        System.out.println("Factorial of " + n + " is: " + factorial(n));
    }
}

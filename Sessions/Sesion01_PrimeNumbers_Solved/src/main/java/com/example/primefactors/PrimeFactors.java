package com.example.primefactors;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactors {

    /**
     * Generates the list of prime factors for a given integer n.
     * For example: 12 -> [2, 2, 3]
     */
    public static List<Integer> generate(int n) {
        List<Integer> factors = new ArrayList<>();
        int divisor = 2;
        // Keep dividing n by the smallest possible divisor
        while (n > 1) {
            while (n % divisor == 0) {
                factors.add(divisor);  // Add the factor to the list
                n /= divisor;          // Divide n by the factor
            }
            divisor++;  // Move to the next possible divisor
        }

        return factors;
    }
}

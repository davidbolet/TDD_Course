package com.example.fizzbuzz;

// This is a simple FizzBuzz class that can be used to demonstrate TDD principles.
// It currently does not contain any functionality, but it serves as a starting point for implementing FizzBuzz logic.
// The main method is empty, and you can add your FizzBuzz logic here
// or in a separate method as you develop your tests and functionality
// 	•	If a number is divisible by 3, return "Fizz".
//	•	If divisible by 5, return "Buzz".
//	•	If divisible by both, return "FizzBuzz".
//	•	Else, return the number as a string.
public class FizzBuzz {
    public static String convert(int number) {
        if (number % 15 == 0) {
            return "FizzBuzz";
        }
        if (number % 3 == 0) {
            return "Fizz";
        }
        if (number % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(number);
    }
}

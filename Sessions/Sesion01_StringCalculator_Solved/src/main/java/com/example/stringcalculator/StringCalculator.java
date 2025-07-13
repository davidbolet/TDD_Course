package com.example.stringcalculator;

import org.hibernate.NotImplementedYetException;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

/**
 * 
Create a simple String calculator with a method signature:
———————————————
int Add(string numbers)
———————————————
The method can take up to two numbers, separated by commas, and will return their sum. 
for example “” or “1” or “1,2” as inputs.
(for an empty string it will return 0) 
Hints:
——————
 - Start with the simplest test case of an empty string and move to one and two numbers
 - Remember to solve things as simply as possible so that you force yourself to write tests you did not think about
 - Remember to refactor after each passing test
———————————————————————————————
Allow the Add method to handle an unknown amount of numbers
————————————————————————————————
Allow the Add method to handle new lines between numbers (instead of commas).
the following input is ok: “1\n2,3” (will equal 6)
the following input is NOT ok: “1,\n” (not need to prove it - just clarifying)
——————————————————————————————-
Support different delimiters
to change a delimiter, the beginning of the string will contain a separate line that looks like this: “//[delimiter]\n[numbers…]” for example “//;\n1;2” should return three where the default delimiter is ‘;’ .
the first line is optional. all existing scenarios should still be supported
————————————————————————————————
Calling Add with a negative number will throw an exception “negatives not allowed” - and the negative that was passed. 
if there are multiple negatives, show all of them in the exception message.
————————————————————————————————
STOP HERE if you are a beginner. Continue if you can finish the steps so far in less than 30 minutes.
————————————————————————————————
Numbers bigger than 1000 should be ignored, so adding 2 + 1001 = 2
————————————————————————————————
Delimiters can be of any length with the following format: “//[delimiter]\n” for example: “//[***]\n1***2***3” should return 6
————————————————————————————————
Allow multiple delimiters like this: “//[delim1][delim2]\n” for example “//[*][%]\n1*2%3” should return 6.
————————————————————————————————
make sure you can also handle multiple delimiters with length longer than one char
 */
public class StringCalculator {

    public int add(String input) {
        if (input == null || input.isEmpty()) return 0; // Paso 1

        // Paso 5: Soporte delimitadores personalizados
		String delimiter = ",|\n";
		if (input.startsWith("//")) {
			if (input.startsWith("//[")) {
				int delimiterEndIndex = input.indexOf("\n");
				String delimiterSection = input.substring(2, delimiterEndIndex); // e.g. "[***][%%%]"

				Matcher matcher = Pattern.compile("\\[(.*?)\\]").matcher(delimiterSection);
				List<String> delimiters = new ArrayList<>();
				while (matcher.find()) {
					delimiters.add(Pattern.quote(matcher.group(1)));
				}
				delimiter = String.join("|", delimiters);
				input = input.substring(delimiterEndIndex + 1);
			} else {
				delimiter = Pattern.quote(String.valueOf(input.charAt(2)));
				input = input.substring(4); // Skip "//x\n"
			}
		}

        // Paso 2-4: Separación usando delimitadores y suma
        String[] numbers = input.split(delimiter);
        List<Integer> intNumbers = Arrays.stream(numbers)
            .map(n -> n.trim().isEmpty() ? "0" : n.trim())
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        // Paso 6: Excepción con negativos
        List<Integer> negatives = intNumbers.stream().filter(n -> n < 0).collect(Collectors.toList());
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negatives.toString());
        }

        // Paso 7: Ignorar números > 1000
        return intNumbers.stream().filter(n -> n <= 1000).reduce(0, Integer::sum);
    }
}
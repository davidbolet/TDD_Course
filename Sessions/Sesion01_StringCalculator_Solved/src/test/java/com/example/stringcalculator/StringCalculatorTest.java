package com.example.stringcalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringCalculatorTest {

    StringCalculator calc = new StringCalculator();

    @Test
    public void testEmptyStringReturnsZero() {
        assertEquals(0, calc.add(""));
    }

    @Test
    public void testSingleNumber() {
        assertEquals(1, calc.add("1"));
    }

    @Test
    public void testTwoNumbersCommaDelimited() {
        assertEquals(3, calc.add("1,2"));
    }

    @Test
    public void testMultipleNumbers() {
        assertEquals(10, calc.add("1,2,3,4"));
    }

    @Test
    public void testNewLineAsDelimiter() {
        assertEquals(6, calc.add("1\n2,3"));
    }

    @Test
    public void testCustomDelimiter() {
        assertEquals(3, calc.add("//;\n1;2"));
    }

    @Test
    public void testNegativeNumbersThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.add("1,-2,-3");
        });
        assertTrue(exception.getMessage().contains("-2"));
        assertTrue(exception.getMessage().contains("-3"));
    }

    @Test
    public void testIgnoreNumbersGreaterThan1000() {
        assertEquals(2, calc.add("2,1001"));
    }

    @Test
    public void testDelimiterOfAnyLength() {
        assertEquals(6, calc.add("//[***]\n1***2***3"));
    }

    @Test
    public void testMultipleDelimiters() {
        assertEquals(6, calc.add("//[*][%]\n1*2%3"));
    }

    @Test
    public void testMultipleDelimitersWithLength() {
        assertEquals(6, calc.add("//[***][%%%]\n1***2%%%3"));
    }
}
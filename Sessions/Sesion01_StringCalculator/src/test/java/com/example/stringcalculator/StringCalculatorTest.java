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

}
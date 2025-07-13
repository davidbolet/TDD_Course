package com.example.fizzbuzz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FizzBuzzTest {

	// This test checks if the FizzBuzz class is functioning correctly.
    @Test
    void shouldReturn1WhenInputIs1() {
        assertEquals("1", FizzBuzz.convert(1));
    }

}

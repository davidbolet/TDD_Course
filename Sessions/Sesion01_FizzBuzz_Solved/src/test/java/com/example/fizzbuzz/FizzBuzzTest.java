package com.example.fizzbuzz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FizzBuzzTest {

    @Test
    void shouldReturn1WhenInputIs1() {
        assertEquals("1", FizzBuzz.convert(1));
    }

    @Test
    void shouldReturn2WhenInputIs2() {
        assertEquals("2", FizzBuzz.convert(2));
    }

    @Test
    void shouldReturnFizzWhenInputIs3() {
        assertEquals("Fizz", FizzBuzz.convert(3));
    }

    @Test
    void shouldReturnBuzzWhenInputIs5() {
        assertEquals("Buzz", FizzBuzz.convert(5));
    }

    @Test
    void shouldReturnFizzWhenInputIs6() {
        assertEquals("Fizz", FizzBuzz.convert(6));
    }

    @Test
    void shouldReturnBuzzWhenInputIs10() {
        assertEquals("Buzz", FizzBuzz.convert(10));
    }

    @Test
    void shouldReturnFizzBuzzWhenInputIs15() {
        assertEquals("FizzBuzz", FizzBuzz.convert(15));
    }

    @Test
    void shouldReturnFizzBuzzWhenInputIs30() {
        assertEquals("FizzBuzz", FizzBuzz.convert(30));
    }
}

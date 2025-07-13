package com.example.primefactors;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimeFactorsTest {

    @Test
    void one_returnsEmptyList() {
        assertEquals(List.of(), PrimeFactors.generate(1));
    }

    @Test
    void two_returns2() {
        assertEquals(List.of(2), PrimeFactors.generate(2));
    }

    @Test
    void three_returns3() {
        assertEquals(List.of(3), PrimeFactors.generate(3));
    }

    @Test
    void four_returns2x2() {
        assertEquals(List.of(2, 2), PrimeFactors.generate(4));
    }

    @Test
    void six_returns2x3() {
        assertEquals(List.of(2, 3), PrimeFactors.generate(6));
    }

    @Test
    void eight_returns2x2x2() {
        assertEquals(List.of(2, 2, 2), PrimeFactors.generate(8));
    }

    @Test
    void nine_returns3x3() {
        assertEquals(List.of(3, 3), PrimeFactors.generate(9));
    }

    @Test
    void largeNumber() {
        assertEquals(List.of(2, 2, 3, 5), PrimeFactors.generate(60));
    }
}
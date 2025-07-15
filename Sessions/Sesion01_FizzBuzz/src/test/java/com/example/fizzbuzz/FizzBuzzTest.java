package com.example.fizzbuzz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FizzBuzzTest {

	@Test
	public void shouldReturn1WhenInputIsOne() {
		assertEquals("1", FizzBuzz.convert(1));
	}

	@Test
	public void shouldReturn2WhenInputIsTwo() {
		assertEquals("2", FizzBuzz.convert(2));
	}

	@Test
	public void shouldReturnFizzWhenInputIsThree() {
		assertEquals("Fizz", FizzBuzz.convert(3));
	}

	@Test
	public void shouldReturnFizzWhenInputIsFive() {
		assertEquals("Buzz", FizzBuzz.convert(5));
	}

	@Test
	public void shouldReturnFizzBuzzWhenInputIsFifteen() {
		assertEquals("FizzBuzz", FizzBuzz.convert(15));
	}
	@Test
	public void shouldReturnFizzWhenInputIsSix() {
		assertEquals("FizzBuzz", FizzBuzz.convert(30));
	}
}

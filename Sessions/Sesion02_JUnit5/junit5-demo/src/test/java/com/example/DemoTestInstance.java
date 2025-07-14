package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DemoTestInstance {
	
	private int counter = 0;

	@BeforeAll
	static void setup() {
		System.out.println("Setup before all tests.");
	}

	@Test
	@DisplayName("Add 5 to counter")
	void add5to_counter() 
	{ 
		counter += 5; 
	}
	
	@Test
	@DisplayName("Add 10 to counter")
	void add10_to_counter() { 
		counter += 10; }
	
	@AfterEach
	void tearDown() {
		System.out.println("Counter value after test: " + counter);	
	}
	
}

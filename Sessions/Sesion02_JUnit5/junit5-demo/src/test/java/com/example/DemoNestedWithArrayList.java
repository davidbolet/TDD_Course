package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

@DisplayName("An ArrayList")
public class DemoNestedWithArrayList {

	private List<String> list;

	@BeforeAll
	static void setup() {
		System.out.println("Setup before all tests.");
	}

	@Nested
	@DisplayName("when new")
	class WhenNew {

		@BeforeEach
		void setUp() {
			list = new ArrayList<>();
		}

		@Test
		@DisplayName("is empty")
		void isEmpty() {
			assertTrue(list.isEmpty());
		}

		@Nested
		@DisplayName("after adding an element")
		class AfterAddElement {

			@BeforeEach
			void setUp() {
				list.add("an item");
			}

			@Test
			@DisplayName("is no longer empty")
			void isNotEmpty() {
				assertTrue(!list.isEmpty());
			}
		}
	}

	
}

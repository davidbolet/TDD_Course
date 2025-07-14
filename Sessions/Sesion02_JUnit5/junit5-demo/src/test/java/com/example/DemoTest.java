package com.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DemoTest {

    @BeforeAll
    static void setup() {
        System.out.println("Setup before all tests.");
    }

    @Test
	@DisplayName("A simple test")
    void simpleTest() {
        assertEquals(2, 1 + 1);
    }

    @Nested
    class NestedTests {

        @Test
        void testInNested() {
            assertTrue("hello".startsWith("h"));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void parameterizedTest(int value) {
        assertTrue(value > 0);
    }

    @RepeatedTest(3)
    void repeatedTest(RepetitionInfo info) {
        System.out.println("Repetition: " + info.getCurrentRepetition());
        assertTrue(info.getTotalRepetitions() == 3);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        List<String> inputs = List.of("a", "bb", "ccc");
        return inputs.stream()
                .map(str -> DynamicTest.dynamicTest("Length of " + str, 
                    () -> assertTrue(str.length() > 0)));
    }

    @TestTemplate
    @ExtendWith(MyTestTemplateProvider.class)
    void testTemplateExample(String input) {
		System.out.println("testTemplateExample: " + input);
        assertNotNull(input);
    }
}

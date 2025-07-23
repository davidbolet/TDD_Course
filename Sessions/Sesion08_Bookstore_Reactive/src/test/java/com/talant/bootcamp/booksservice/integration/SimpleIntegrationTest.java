package com.talant.bootcamp.booksservice.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Simple Integration Test")
class SimpleIntegrationTest {
    
    @Test
    @DisplayName("Should load Spring context")
    void shouldLoadSpringContext() {
        assertTrue(true, "Spring context loaded successfully");
    }
} 
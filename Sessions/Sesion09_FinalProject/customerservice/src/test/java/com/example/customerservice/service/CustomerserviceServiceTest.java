
package com.example.customerservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CustomerserviceServiceTest {

    @Mock
    private SomeDependency someDependency;

    @InjectMocks
    private CustomerserviceService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSomething() {
        // Arrange

        // Act

        // Assert
    }
}

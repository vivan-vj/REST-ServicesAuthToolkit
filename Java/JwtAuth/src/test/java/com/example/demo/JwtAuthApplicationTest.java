package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

/**
 * The type Jwt auth application test.
 *
 * @author Vivek Jadhav
 */
class JwtAuthApplicationTest {

    /**
     * Main calls spring application run.
     */
    @Test
    void mainCallsSpringApplicationRun() {
        try (MockedStatic<SpringApplication> mockedSpringApp = mockStatic(SpringApplication.class)) {
            String[] args = {"arg1", "arg2"};
            JwtAuthApplication.main(args);
            mockedSpringApp.verify(() -> SpringApplication.run(JwtAuthApplication.class, args));
        }
    }
}
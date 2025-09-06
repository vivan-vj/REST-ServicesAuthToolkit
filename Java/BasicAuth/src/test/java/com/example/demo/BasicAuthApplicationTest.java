package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class BasicAuthApplicationTest {

    @Test
    void main_shouldCallSpringApplicationRun() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            BasicAuthApplication.main(new String[]{"arg1", "arg2"});
            mockedSpringApplication.verify(() -> SpringApplication.run(BasicAuthApplication.class, new String[]{"arg1", "arg2"}));
        }
    }
}
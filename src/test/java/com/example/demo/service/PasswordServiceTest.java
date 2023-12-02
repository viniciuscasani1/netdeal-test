package com.example.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

    @InjectMocks
    private PasswordService passwordService;

    @Mock
    private AlphaSequentialService alphaSequentialService;
    @Mock
    private NumberSequentialService numberSequentialService;
    @Mock
    private SymbolSequentialService symbolSequentialService;

    @BeforeEach
    public void setup() {
        Mockito.when(numberSequentialService.getSequence()).thenCallRealMethod();
        Mockito.when(alphaSequentialService.getSequence()).thenCallRealMethod();
        Mockito.when(symbolSequentialService.getSequence()).thenCallRealMethod();
        Mockito.when(numberSequentialService.countSequentialItems(Mockito.any())).thenCallRealMethod();
        Mockito.when(alphaSequentialService.countSequentialItems(Mockito.any())).thenCallRealMethod();
        Mockito.when(symbolSequentialService.countSequentialItems(Mockito.any())).thenCallRealMethod();
    }

    @Test
    public void should_test_pass_with_lower_case_letters_and_numbers() {
        String pass = "abcdeaaa123";

        int score = passwordService.calculatePasswordStrength(pass);

        Assertions.assertEquals(34, score);
    }

    @Test
    public void should_test_pass_with_upper_case_letters_and_numbers() {
        String pass = "ABCDEAAA123";

        int score = passwordService.calculatePasswordStrength(pass);

        Assertions.assertEquals(34, score);
    }

    @Test
    public void should_test_pass_with_minimum_requirements() {
        String pass = "aBCDE!@AA123";

        int score = passwordService.calculatePasswordStrength(pass);

        Assertions.assertEquals(97, score);
    }
}
package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.dto.BookRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookValidationTests {
    private static Validator validator;
    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testAllValidFields() {
        var request = new BookRequest("0123456789", "title", "author", 5.45);
        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidBookIsbn() {
        var request = new BookRequest("ab23456789", "title", "author", 5.45);
        var violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}

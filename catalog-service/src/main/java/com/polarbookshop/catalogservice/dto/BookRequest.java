package com.polarbookshop.catalogservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRequest(
        @NotBlank(message = "Book ISBN is Required")
        @Pattern(regexp="^([0-9]{10}|[0-9]{13})$",message = "The Book ISBN Format Isn't Valid")
        String isbn,
        @NotBlank(message = "Book Title is Required")
        String title,
        @NotBlank(message = "Book Author Name is Required")
        String author,
        @NotNull(message = "Book Price is Mandatory")
        @Positive(message = "Book Price Should Be Positive Value")
        Double price
) {
}

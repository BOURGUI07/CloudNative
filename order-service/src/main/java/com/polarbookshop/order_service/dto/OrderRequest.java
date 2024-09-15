package com.polarbookshop.order_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderRequest(
        @NotBlank(message = "Book ISBN is Required")
        String bookIsbn,
        @NotNull(message = "Book Quantity is Required")
        @Min(value=1, message="You Must Order at Least One Item.")
        @Max(value=5, message="You Cannot Order More Than 5 Items.")
        Integer quantity
) {
}

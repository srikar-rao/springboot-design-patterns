package com.dev.org.discount.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String bookName;
    private double basePrice;
    private String discountMode;
}

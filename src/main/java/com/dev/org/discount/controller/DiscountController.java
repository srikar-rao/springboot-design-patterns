package com.dev.org.discount.controller;

import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;
import com.dev.org.discount.service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/price")
    public ResponseEntity<DiscountResponse> getBookPrice(@RequestBody Book book) {
        DiscountResponse response = discountService.calculateDiscount(book);
        return ResponseEntity.ok(response);
    }
}

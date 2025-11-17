package com.dev.org.discount.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse {
    private String bookName;
    private double originalPrice;
    private double finalPrice;
    private List<DiscountInfo> discountsApplied;
    private List<String> notes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiscountInfo {
        private String discountType;
        private double amount;
        private String description;
    }
}

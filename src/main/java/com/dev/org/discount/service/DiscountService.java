package com.dev.org.discount.service;

import com.dev.org.discount.chain.ChainedDiscountProcessor;
import com.dev.org.discount.chain.DiscountHandler;
import com.dev.org.discount.composite.CompositeDiscount;
import com.dev.org.discount.exception.InvalidDiscountException;
import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;
import com.dev.org.discount.rule.HolidayDiscountRule;
import com.dev.org.discount.rule.NoDiscountRule;
import com.dev.org.discount.rule.SpecificationBasedDiscountRule;
import com.dev.org.discount.specification.BulkPurchaseSpecification;
import com.dev.org.discount.specification.StartsWithCSpecification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService {

    public DiscountResponse calculateDiscount(Book book) {
        List<DiscountResponse.DiscountInfo> appliedDiscounts = new ArrayList<>();
        double totalDiscount;

        switch (book.getDiscountMode().toUpperCase()) {
            case "COMPOSITE":
                totalDiscount = applyCompositeDiscount(book, appliedDiscounts);
                break;
            case "CHAINED":
                totalDiscount = applyChainedDiscount(book, appliedDiscounts);
                break;
            case "NONE":
                totalDiscount = new NoDiscountRule().apply(book, appliedDiscounts);
                break;
            default:
                throw new InvalidDiscountException("Unsupported discount mode: " + book.getDiscountMode());
        }

        double finalPrice = book.getBasePrice() - totalDiscount;
        if (finalPrice < 0) {
            finalPrice = 0; // Guard condition
        }

        return new DiscountResponse(
                book.getBookName(),
                book.getBasePrice(),
                finalPrice,
                appliedDiscounts,
                List.of(book.getDiscountMode() + " discount mode applied", "Final price validated not below $0")
        );
    }

    private double applyCompositeDiscount(Book book, List<DiscountResponse.DiscountInfo> appliedDiscounts) {
        CompositeDiscount composite = new CompositeDiscount();
        composite.addDiscount(new SpecificationBasedDiscountRule(new StartsWithCSpecification(), 5.00, "NameStartsWithCDiscount", "Book name starts with 'C'"));
        composite.addDiscount(new HolidayDiscountRule());
        composite.addDiscount(new SpecificationBasedDiscountRule(new BulkPurchaseSpecification(), 20.00, "BulkPurchaseDiscount", "Bulk purchase offer"));
        return composite.apply(book, appliedDiscounts);
    }

    private double applyChainedDiscount(Book book, List<DiscountResponse.DiscountInfo> appliedDiscounts) {
        DiscountHandler startsWithCHandler = new ChainedDiscountProcessor(new SpecificationBasedDiscountRule(new StartsWithCSpecification(), 5.00, "NameStartsWithCDiscount", "Book name starts with 'C'"));
        DiscountHandler holidayHandler = new ChainedDiscountProcessor(new HolidayDiscountRule());

        startsWithCHandler.setNextHandler(holidayHandler);

        return startsWithCHandler.applyDiscount(book, appliedDiscounts);
    }
}

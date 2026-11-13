package de.slauth.shop.discount;

import de.slauth.shop.Customer;
import de.slauth.shop.Order;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

/// Exposes the [DiscountCalculator] via HTTP.
///
/// Example: `GET /api/discount?total=150.00&memberSince=2020-01-01`
@RestController
public class DiscountController {

    private final DiscountCalculator calculator;

    public DiscountController(DiscountCalculator calculator) {
        this.calculator = calculator;
    }

    @GetMapping("/api/discount")
    public DiscountResponse discount(
            @RequestParam BigDecimal total,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate memberSince) {
        var discount = calculator.discountFor(new Customer(memberSince), new Order(total));
        return new DiscountResponse(discount);
    }

    public record DiscountResponse(BigDecimal discount) {
    }
}

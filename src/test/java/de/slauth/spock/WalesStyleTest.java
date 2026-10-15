package de.slauth.spock;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WalesStyleTest {

    private final DiscountCalculator calculator = new DiscountCalculator();

    @Test
    void memberSinceOneYearAndOrderOf100gets5discount() {
        Customer customer = new Customer(LocalDate.now().minusYears(1));
        Order order = new Order(new BigDecimal("100.00"));
        BigDecimal discount = calculator.discountFor(customer, order);
        assertEquals(new BigDecimal("5.00"), discount);
    }

    @Test
    void memberSinceTwoYearsAndOrderOf100gets15discount() {
        Customer customer = new Customer(LocalDate.now().minusYears(2));
        Order order = new Order(new BigDecimal("100.00"));
        BigDecimal discount = calculator.discountFor(customer, order);
        assertEquals(new BigDecimal("15.00"), discount);
    }
}

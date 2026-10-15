package de.slauth.spock;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyFirstTest {

    private final DiscountCalculator calculator = new DiscountCalculator();

    @Test
    void calculatesDiscount() {
        Customer customer = new Customer(LocalDate.now().minusYears(2));
        Order order = new Order(new BigDecimal("100.00"));
        BigDecimal discount = calculator.discountFor(customer, order);
        assertEquals(new BigDecimal("15.00"), discount);
    }
}

package de.slauth.shop.discount;

import de.slauth.shop.Customer;
import de.slauth.shop.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WalesStyleTest {

    static final LocalDate today = LocalDate.parse("2026-12-09");
    static final Clock clock = Clock.fixed(Instant.parse("2026-12-09T17:00:00+01:00"), ZoneId.of("Europe/Berlin"));

    private final DiscountCalculator calculator = new DiscountCalculator(clock);

    @Test
    void memberSince2YearsAndOrderOf100gets15discount() {
        // given
        var customer = new Customer(today.minusYears(2));
        var order = new Order(new BigDecimal("100.00"));

        // when
        var discount = calculator.discountFor(customer, order);

        // then
        assertEquals(new BigDecimal("15.00"), discount);
    }
}

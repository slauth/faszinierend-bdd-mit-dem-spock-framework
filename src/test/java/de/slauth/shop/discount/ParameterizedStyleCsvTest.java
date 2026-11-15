package de.slauth.shop.discount;

import de.slauth.shop.Customer;
import de.slauth.shop.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class ParameterizedStyleCsvTest {

    static final Clock clock = Clock.fixed(Instant.parse("2026-12-09T17:00:00+01:00"), ZoneId.of("Europe/Berlin"));

    DiscountCalculator calculator = new DiscountCalculator(clock);

    @ParameterizedTest
    @CsvSource(textBlock = """
            2026-12-09,  99.00,  0.00
            2026-12-09, 100.00,  5.00
            2025-12-09,  99.00,  0.00
            2024-12-09,  99.00,  9.90
            2024-12-09, 100.00, 15.00
            """)
    void calculatesDiscount(LocalDate memberSince, BigDecimal total, BigDecimal expectedDiscount) {

        // given
        var customer = new Customer(memberSince);
        var order = new Order(total);

        // when
        var actualDiscount = calculator.discountFor(customer, order);

        // then
        assertThat(actualDiscount).isEqualTo(expectedDiscount);
    }
}

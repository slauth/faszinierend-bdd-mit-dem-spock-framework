package de.slauth.spock;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ParameterizedStyleTest {

	static final LocalDate today = LocalDate.now();

	DiscountCalculator calculator = new DiscountCalculator();

	@ParameterizedTest
	@MethodSource("discountCases")
	void calculatesDiscount(LocalDate memberSince, BigDecimal total, BigDecimal expectedDiscount) {

		// given
		var customer = new Customer(memberSince);
		var order = new Order(total);

		// when
		var actualDiscount = calculator.discountFor(customer, order);

		// then
		assertThat(actualDiscount).isEqualTo(expectedDiscount);
	}

	static Stream<Arguments> discountCases() {
		return Stream.of(
				Arguments.of(today, new BigDecimal("99.00"), new BigDecimal("0.00")),
				Arguments.of(today, new BigDecimal("100.00"), new BigDecimal("5.00")),
				Arguments.of(today.minusYears(1), new BigDecimal("99.00"), new BigDecimal("0.00")),
				Arguments.of(today.minusYears(2), new BigDecimal("99.00"), new BigDecimal("9.90")),
				Arguments.of(today.minusYears(2), new BigDecimal("100.00"), new BigDecimal("15.00"))
		);
	}
}

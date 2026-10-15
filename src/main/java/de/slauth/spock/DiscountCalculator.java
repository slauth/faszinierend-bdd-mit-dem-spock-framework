package de.slauth.spock;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Period;

import static java.math.RoundingMode.HALF_UP;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.YEARS;

/// Calculates the discount for an order.
/// - Orders of 100 € or more get 5 % off.
/// - Customers who have been members for at least 2 years get *another* 10 % off.
@Service
public class DiscountCalculator {

    public BigDecimal discountFor(Customer customer, Order order) {
        int percent = 0;
        if (isLargeOrder(order)) {
            percent += 5;
        }
        if (isLoyalMember(customer)) {
            percent += 10;
        }
        return order.total()
                .multiply(BigDecimal.valueOf(percent))
                .divide(BigDecimal.valueOf(100), 2, HALF_UP);
    }

    private boolean isLargeOrder(Order order) {
        var threshold = new BigDecimal("100.00");
        return order.total().compareTo(threshold) >= 0;
    }

    private boolean isLoyalMember(Customer customer) {
        var memberSince = customer.memberSince();
        if (memberSince == null) {
            return false;
        }
        var threshold = now().minus(Period.ofYears(2));
        return !memberSince.isAfter(threshold);
    }
}

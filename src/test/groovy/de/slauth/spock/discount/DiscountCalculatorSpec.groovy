package de.slauth.spock.discount

import de.slauth.spock.Customer
import de.slauth.spock.DiscountCalculator
import de.slauth.spock.Order
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

class DiscountCalculatorSpec extends Specification {

    @Shared
    def today = LocalDate.now()

    def calculator = new DiscountCalculator()

    void "member since #memberSince, order of #total € gets #expected € discount" (
            LocalDate memberSince, BigDecimal total, BigDecimal expected) {
        given:
            def customer = new Customer(memberSince)
            def order = new Order(total)

        when:
            def actual = calculator.discountFor(customer, order)

        then:
            actual == expected

        where:
            memberSince         |  total || expected
            today               |  99.00 ||     0.00
            today               | 100.00 ||     5.00
            today.minusYears(1) |  99.00 ||     0.00
            today.minusYears(2) |  99.00 ||     9.90
            today.minusYears(2) | 100.00 ||    15.00
    }
}

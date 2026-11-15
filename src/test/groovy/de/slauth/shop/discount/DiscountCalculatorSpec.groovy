package de.slauth.shop.discount

import de.slauth.shop.Customer
import de.slauth.shop.Order
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

class DiscountCalculatorSpec extends Specification {

    @Shared
    def today = LocalDate.now()

    def calculator = new DiscountCalculator()

    void "member since #memberSince, order of #total EUR gets #expected EUR discount" () {

        given: "a customer with membership since #memberSince and with an order of #total EUR"
            def customer = new Customer(memberSince)
            def order = new Order(total)

        expect: "a discount of #expected EUR"
            calculator.discountFor(customer, order) == expected

        where: "the discount rules are"
            memberSince         |  total || expected
            today               |  99.00 ||     0.00
            today               | 100.00 ||     5.00
            today.minusYears(1) |  99.00 ||     0.00
            today.minusYears(2) |  99.00 ||     9.90
            today.minusYears(2) | 100.00 ||    15.00
    }
}

package de.slauth.shop.checkout


import de.slauth.shop.Customer
import de.slauth.shop.discount.DiscountCalculator
import de.slauth.shop.Order
import spock.lang.Specification

import java.time.LocalDate

class CheckoutServiceSpec extends Specification {

    def today = LocalDate.now()

    def calculator = Mock(DiscountCalculator)
    def paymentGateway = Mock(PaymentGateway)
    def confirmationMailer = Mock(ConfirmationMailer)

    def service = new CheckoutService(calculator, paymentGateway, confirmationMailer)

    void "charges the order total minus the discount"() {

        given: "a new customer with an order of 100.00 EUR"
            def customer = new Customer(today)
            def order = new Order(100.00)

        when: "the customer checks out"
            service.checkout(customer, order)

        then: "the discount calculator is applied"
            1 * calculator.discountFor(customer, order) >> 5.00

        and: "the discounted total is charged"
            1 * paymentGateway.charge(customer, 95.00)
    }

    void "sends an order confirmation"() {

        given: "a new customer with an order of 100.00 EUR"
            def customer = new Customer(today)
            def order = new Order(100.00)

        when: "the customer checks out"
            service.checkout(customer, order)

        then: "the discount calculator is applied"
            1 * calculator.discountFor(customer, order) >> 5.00

        and: "a confirmation with order number, discount and total is sent"
            1 * confirmationMailer.send({ OrderConfirmation it ->
                it.orderNumber != null
                it.customer == customer
                it.discount == 5.00
                it.total == 95.00
            })
    }
}

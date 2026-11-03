package de.slauth.shop.checkout


import de.slauth.shop.Customer
import de.slauth.shop.discount.DiscountCalculator
import de.slauth.shop.Order
import spock.lang.Specification

class CheckoutServiceSpec extends Specification {

    def discountCalculator = Mock(DiscountCalculator)
    def paymentGateway = Mock(PaymentGateway)

    def service = new CheckoutService(discountCalculator, paymentGateway)

    void "charges the order total minus the discount"() {
        given:
            def customer = new Customer(null)
            def order = new Order(100.00)

        when:
            service.checkout(customer, order)

        then:
            1 * discountCalculator.discountFor(customer, order) >> 15.00
            1 * paymentGateway.charge(customer, 85.00)
    }
}

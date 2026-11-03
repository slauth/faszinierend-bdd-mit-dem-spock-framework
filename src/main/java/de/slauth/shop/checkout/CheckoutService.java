package de.slauth.shop.checkout;

import de.slauth.shop.Customer;
import de.slauth.shop.discount.DiscountCalculator;
import de.slauth.shop.Order;

/// Checks out an order: applies the discount and charges the customer.
public class CheckoutService {

    private final DiscountCalculator discountCalculator;
    private final PaymentGateway paymentGateway;

    public CheckoutService(DiscountCalculator discountCalculator, PaymentGateway paymentGateway) {
        this.discountCalculator = discountCalculator;
        this.paymentGateway = paymentGateway;
    }

    public void checkout(Customer customer, Order order) {
        var discount = discountCalculator.discountFor(customer, order);
        var totalDiscounted = order.total().subtract(discount);
        paymentGateway.charge(customer, totalDiscounted);
    }
}

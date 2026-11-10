package de.slauth.shop.checkout;

import de.slauth.shop.Customer;
import de.slauth.shop.discount.DiscountCalculator;
import de.slauth.shop.Order;

import java.util.UUID;

/// Checks out an order: applies the discount, charges the customer and sends a confirmation.
public class CheckoutService {

    private final DiscountCalculator discountCalculator;
    private final PaymentGateway paymentGateway;
    private final ConfirmationMailer confirmationMailer;

    public CheckoutService(DiscountCalculator discountCalculator, PaymentGateway paymentGateway,
                           ConfirmationMailer confirmationMailer) {
        this.discountCalculator = discountCalculator;
        this.paymentGateway = paymentGateway;
        this.confirmationMailer = confirmationMailer;
    }

    public void checkout(Customer customer, Order order) {
        var discount = discountCalculator.discountFor(customer, order);
        var totalDiscounted = order.total().subtract(discount);
        paymentGateway.charge(customer, totalDiscounted);
        confirmationMailer.send(new OrderConfirmation(UUID.randomUUID(), customer, discount, totalDiscounted));
    }
}

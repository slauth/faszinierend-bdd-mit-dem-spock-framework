package de.slauth.shop.checkout;

import de.slauth.shop.Customer;

import java.math.BigDecimal;

/// Charges a customer via an external payment provider.
public interface PaymentGateway {

    void charge(Customer customer, BigDecimal amount);
}

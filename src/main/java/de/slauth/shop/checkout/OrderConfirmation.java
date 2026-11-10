package de.slauth.shop.checkout;

import de.slauth.shop.Customer;

import java.math.BigDecimal;
import java.util.UUID;

/// Confirmation sent to the customer after a successful checkout.
public record OrderConfirmation(UUID orderNumber, Customer customer, BigDecimal discount, BigDecimal total) {
}

package de.slauth.shop.checkout;

/// Sends order confirmations to customers.
public interface ConfirmationMailer {

    void send(OrderConfirmation confirmation);
}

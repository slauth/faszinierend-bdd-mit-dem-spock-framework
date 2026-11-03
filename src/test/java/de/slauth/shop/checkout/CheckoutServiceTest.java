package de.slauth.shop.checkout;

import de.slauth.shop.Customer;
import de.slauth.shop.Order;
import de.slauth.shop.discount.DiscountCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CheckoutServiceTest {

    private final DiscountCalculator discountCalculator = mock(DiscountCalculator.class);
    private final PaymentGateway paymentGateway = mock(PaymentGateway.class);

    private final CheckoutService service = new CheckoutService(discountCalculator, paymentGateway);

    @Test
    void chargesTheOrderTotalMinusTheDiscount() {
        // given
        Customer customer = new Customer(null);
        Order order = new Order(new BigDecimal("100.00"));
        when(discountCalculator.discountFor(customer, order))
                .thenReturn(new BigDecimal("15.00"));

        // when
        service.checkout(customer, order);

        // then
        verify(discountCalculator, times(1)).discountFor(customer, order);
        verify(paymentGateway, times(1)).charge(customer, new BigDecimal("85.00"));
    }
}

package de.slauth.shop.checkout;

import de.slauth.shop.Customer;
import de.slauth.shop.Order;
import de.slauth.shop.discount.DiscountCalculator;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CheckoutServiceTest {

    private final LocalDate today = LocalDate.now();

    private final DiscountCalculator calculator = mock(DiscountCalculator.class);
    private final PaymentGateway paymentGateway = mock(PaymentGateway.class);
    private final ConfirmationMailer confirmationMailer = mock(ConfirmationMailer.class);

    private final CheckoutService service = new CheckoutService(calculator, paymentGateway, confirmationMailer);

    @Test
    void chargesTheOrderTotalMinusTheDiscount() {
        // given
        Customer customer = new Customer(today);
        Order order = new Order(new BigDecimal("100.00"));
        when(calculator.discountFor(customer, order))
                .thenReturn(new BigDecimal("15.00"));

        // when
        service.checkout(customer, order);

        // then
        verify(calculator, times(1)).discountFor(customer, order);
        verify(paymentGateway, times(1)).charge(customer, new BigDecimal("85.00"));
    }

    @Test
    void sendsAnOrderConfirmation() {
        // given
        Customer customer = new Customer(today);
        Order order = new Order(new BigDecimal("100.00"));
        when(calculator.discountFor(customer, order))
                .thenReturn(new BigDecimal("15.00"));

        // when
        service.checkout(customer, order);

        // then
        ArgumentCaptor<OrderConfirmation> captor = ArgumentCaptor.forClass(OrderConfirmation.class);
        verify(confirmationMailer, times(1)).send(captor.capture());
        OrderConfirmation confirmation = captor.getValue();
        assertThat(confirmation.orderNumber()).isNotNull();
        assertThat(confirmation.customer()).isEqualTo(customer);
        assertThat(confirmation.discount()).isEqualByComparingTo("15.00");
        assertThat(confirmation.total()).isEqualByComparingTo("85.00");
    }
}

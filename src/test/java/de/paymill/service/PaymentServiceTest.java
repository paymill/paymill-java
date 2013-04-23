package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Payment;
import de.paymill.model.Payment.Type;

public class PaymentServiceTest extends TestCase {

    @Test
    public void testCreateCard() {
        PaymentService srv = Paymill.getService(PaymentService.class);
        String token = getToken();

        Payment payment = srv.create(token);

        assertNotNull(payment);
        assertNotNull(payment.getId());
        assertEquals("1111", payment.getLast4());
    }

    @Test
    public void testCreateDebit() {
        PaymentService srv = Paymill.getService(PaymentService.class);

        Payment params = new Payment();
        params.setType(Type.DEBIT);
        params.setAccount("123456");
        params.setCode("12345678");
        params.setHolder("jon doe");
        Payment payment = srv.create(params);

        assertNotNull(payment);
        assertNotNull(payment.getId());
        assertEquals("**3456", payment.getAccount());
    }

    @Test
    public void testDeletePayment() {
        PaymentService srv = Paymill.getService(PaymentService.class);
        String token = getToken();

        Payment payment = srv.create(token);

        assertNotNull(payment);
        assertNotNull(payment.getId());
        assertEquals("1111", payment.getLast4());

        try {
            srv.delete(payment);
        } catch (NullPointerException e) {
            fail("Deletion should be successful");
            e.printStackTrace();
        }
    }

}

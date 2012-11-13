package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Payment;

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
}

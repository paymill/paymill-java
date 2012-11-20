package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Client;
import de.paymill.model.Offer;
import de.paymill.model.Payment;
import de.paymill.model.Subscription;
import de.paymill.net.ApiException;

public class SubscriptionServiceTest extends TestCase {

	@Test
	public void testCreateSubscription() {
		SubscriptionService srv = Paymill.getService(SubscriptionService.class);
		Offer offer = new Offer();
		offer.setId("offer_12345");
		Client client = new Client();
		client.setId("client_12345");
		Payment pay = new Payment();
		pay.setId("pay_12345");
		
		Subscription params = new Subscription();
		params.setOffer(offer);
		params.setClient(client);
		params.setPayment(pay);

		try {
			srv.create(params);
			fail("Expected exception");
		} catch(ApiException ex) {
			assertEquals("offer_not_found", ex.getCode());
		}
	}
}

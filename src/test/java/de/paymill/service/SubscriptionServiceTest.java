package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Client;
import de.paymill.model.Interval;
import de.paymill.model.Offer;
import de.paymill.model.Payment;
import de.paymill.model.Payment.Type;
import de.paymill.model.Preauthorization;
import de.paymill.model.Subscription;
import java.util.List;

public class SubscriptionServiceTest extends TestCase {

	@Test
	public void testCreateSubscription() {
		OfferService srvOffer = Paymill.getService(OfferService.class);
		ClientService srvClient = Paymill.getService(ClientService.class);
		PaymentService srvPayment = Paymill.getService(PaymentService.class);
		SubscriptionService srvSubs = Paymill.getService(SubscriptionService.class);

		Interval interval = new Interval();
		interval.setInterval(1);
		interval.setUnit(Interval.Unit.WEEK);
		Offer offer = new Offer();
		offer.setAmount(199);
		offer.setInterval(interval);
		offer.setName("testabo");
		offer.setTrialPeriodDays(15);
		offer.setCurrency("EUR");
		offer = srvOffer.create(offer);

		Client client = new Client();
		client.setEmail(getRandomEmail());
		client = srvClient.create(client);

		Payment payment = new Payment();
		payment.setType(Type.DEBIT);
		payment.setAccount("123456");
		payment.setCode("12345678");
		payment.setHolder("jon doe");
		payment.setClient(client.getId());
		payment = srvPayment.create(payment);

		Subscription subs = new Subscription();
		subs.setOffer(offer);
		subs.setClient(client);
		subs.setPayment(payment);
		subs = srvSubs.create(subs);

		assertNotNull(subs);
		assertNotNull(subs.getId());
		assertEquals(subs.getClient().getId(), client.getId());
		assertEquals(subs.getOffer().getId(), offer.getId());
		assertEquals(subs.getPayment().getId(), payment.getId());
	}

	@Test
	public void testUpdateSubscription() {
		OfferService srvOffer = Paymill.getService(OfferService.class);
		PaymentService srvPayment = Paymill.getService(PaymentService.class);
		SubscriptionService srvSubs = Paymill.getService(SubscriptionService.class);

		List<Subscription> list = srvSubs.list(0, 1);
                
                Subscription subs = list.get(0);
            
		Interval interval = new Interval();
		interval.setInterval(2);
		interval.setUnit(Interval.Unit.MONTH);
		Offer offer = new Offer();
		offer.setAmount(200);
		offer.setInterval(interval);
		offer.setName("testabo2");
		offer.setTrialPeriodDays(0);
		offer.setCurrency("EUR");
		offer = srvOffer.create(offer);
 
		Payment payment = new Payment();
		payment.setType(Type.DEBIT);
		payment.setAccount("654321");
		payment.setCode("87654321");
		payment.setHolder("jon doe");
		payment.setClient(subs.getClient().getId());
		payment = srvPayment.create(payment);
                
                subs.setOffer(offer);
                subs.setPayment(payment);
                subs.setCancelAtPeriodEnd(true);
                srvSubs.update(subs);

		assertNotNull(subs);
		assertNotNull(subs.getId());
		assertEquals(subs.getOffer().getId(), offer.getId());
		assertEquals(subs.getPayment().getId(), payment.getId());
       }
        
}

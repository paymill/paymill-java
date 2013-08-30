package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Client;
import de.paymill.model.Interval;
import de.paymill.model.Offer;
import de.paymill.model.Payment;
import de.paymill.model.Subscription;

public class OfferServiceTest extends TestCase {

	@Test
	public void testCreateOffer() {
		OfferService srv = Paymill.getService(OfferService.class);
		Interval interval = new Interval();
		interval.setInterval(1);
		interval.setUnit(Interval.Unit.WEEK);
		Offer params = new Offer();
		params.setAmount(199);
		params.setInterval(interval);
		params.setName("Superabo");
		params.setTrialPeriodDays(15);
		params.setCurrency("EUR");

		Offer offer = srv.create(params);
		assertEquals("Superabo", offer.getName());
		assertNotNull(offer.getId());
	}

	@Test
	public void testGetOffer() {
		OfferService srv = Paymill.getService(OfferService.class);
		Interval interval = new Interval();
		interval.setInterval(1);
		interval.setUnit(Interval.Unit.WEEK);
		Offer params = new Offer();
		params.setAmount(199);
		params.setInterval(interval);
		params.setName("Superabo");
		params.setTrialPeriodDays(15);
		params.setCurrency("EUR");

		Offer offer1 = srv.create(params);
		Offer offer2 = srv.get(offer1.getId());

		assertNotNull(offer2.getId());
		assertEquals(offer1.getId(), offer2.getId());
	}

	@Test
	public void testGetOfferList() {
		int limit=5;
		OfferService srv = Paymill.getService(OfferService.class);
		List<Offer> firstOffers = srv.list(0, limit);
		int firstSize = firstOffers.size();
		assertNotNull(firstOffers);
		assertTrue(firstSize <= limit);
		testCreateOffer();
		List<Offer> secondOffers = srv.list(0, limit);
		int secondSize = secondOffers.size();
		assertNotNull(secondOffers);
		assertTrue(secondSize > 0 && secondSize <= limit);
		assertTrue(secondSize == limit || secondSize == firstSize + 1);
	}

	@Test
	public void testSubscribeOffer() {
		OfferService srvOffer = Paymill.getService(OfferService.class);
		ClientService srvClient = Paymill.getService(ClientService.class);
		PaymentService srvPayment = Paymill.getService(PaymentService.class);

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

		Payment payment = srvPayment.create(getToken(), client);

		Subscription subs = srvOffer.subscribe(offer, client, payment);
		assertNotNull(subs);
		assertNotNull(subs.getId());
		assertEquals(subs.getClient().getId(), client.getId());
		assertEquals(subs.getOffer().getId(), offer.getId());
		assertEquals(subs.getPayment().getId(), payment.getId());
	}
}

package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Offer;
import de.paymill.model.Offer.Interval;

public class OfferServiceTest extends TestCase {

	@Test
	public void testCreateOffer() {
		OfferService srv = Paymill.getService(OfferService.class);
		Offer params = new Offer();
		params.setAmount(199);
		params.setInterval(Interval.WEEK);
		params.setName("Superabo");
		params.setTrialPeriodDays(15);
		params.setCurrency("eur");
		
		Offer offer = srv.create(params);
		assertEquals("Superabo", offer.getName());
		assertNotNull(offer.getId());
	}

	@Test
	public void testGetOffer() {
		OfferService srv = Paymill.getService(OfferService.class);
		Offer params = new Offer();
		params.setAmount(199);
		params.setInterval(Interval.WEEK);
		params.setName("Superabo");
		params.setTrialPeriodDays(15);
		params.setCurrency("eur");
		
		Offer offer1 = srv.create(params);
		Offer offer2 = srv.get(offer1.getId());

		assertNotNull(offer2.getId());
		assertEquals(offer1.getId(), offer2.getId());
	}

	@Test
	public void testGetOfferList() {
		OfferService srv = Paymill.getService(OfferService.class);
		List<Offer> offers = srv.list(0, 5);

		assertNotNull(offers);
		assertEquals(5, offers.size());
	}
}

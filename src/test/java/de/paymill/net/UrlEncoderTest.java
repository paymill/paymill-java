package de.paymill.net;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.paymill.model.Client;
import de.paymill.model.Offer;
import de.paymill.model.Offer.Interval;

public class UrlEncoderTest {

	@Test
	public void testEncodeModel() {
		UrlEncoder encoder = new UrlEncoder();
		Client client = new Client();
		client.setEmail("test@example.com");
		
		assertEquals("email=test%40example.com", encoder.encode(client));
	}
	
	@Test
	public void testEnumCase() {
		UrlEncoder encoder = new UrlEncoder();
		
		Offer offer = new Offer();
		offer.setInterval(Interval.WEEK);
		
		assertEquals("interval=week", encoder.encode(offer));
	}
	
	@Test
	public void testCamelCase() {
		UrlEncoder encoder = new UrlEncoder();
		
		Offer offer = new Offer();
		offer.setTrialPeriodDays(15);
		
		assertEquals("trial_period_days=15", encoder.encode(offer));
	}

}

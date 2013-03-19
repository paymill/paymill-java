package de.paymill.net;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.paymill.model.Client;
import de.paymill.model.Interval;
import de.paymill.model.Offer;

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
		
		Interval interval = new Interval();
		interval.setInterval(1);
		interval.setUnit(Interval.Unit.WEEK);
		Offer offer = new Offer();
		offer.setInterval(interval);
		
		assertEquals("interval=1+WEEK", encoder.encode(offer));
	}
	
	@Test
	public void testCamelCase() {
		UrlEncoder encoder = new UrlEncoder();
		
		Offer offer = new Offer();
		offer.setTrialPeriodDays(15);
		
		assertEquals("trial_period_days=15", encoder.encode(offer));
	}

}

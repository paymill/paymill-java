package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Card;

public class CardServiceTest extends TestCase {

	@Test
	public void testCreateCard() {
		CardService srv = Paymill.getService(CardService.class);
		String token = getToken();

		Card card = srv.create(token);

		assertNotNull(card);
		assertNotNull(card.getId());
		assertEquals("1111", card.getLast4());
	}
}

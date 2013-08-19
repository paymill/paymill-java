package de.paymill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Refund;
import de.paymill.model.Transaction;
import de.paymill.net.ApiException;

public class RefundServiceTest extends TestCase {

	@Test
	public void testCreateRefund() {
		RefundService srv = Paymill.getService(RefundService.class);
		Transaction tx = new Transaction();
		tx.setId("tx_12345");
		Refund params = new Refund();
		params.setAmount(50);
		params.setTransaction(tx);

		try {
			srv.create(params);
			fail("Expected exception");
		} catch (ApiException ex) {
			assertEquals("transaction_not_found", ex.getCode());
		}
	}
	@Test
	public void testGetRefundList() {
		RefundService srv = Paymill.getService(RefundService.class);
		List<Refund> refunds = srv.list(0, 5);

		assertNotNull(refunds);
		assertEquals(5, refunds.size());
	}
}

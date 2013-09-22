package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
		int limit=5;
		RefundService srv = Paymill.getService(RefundService.class);
		List<Refund> firstRefunds = srv.list(0,limit);
		int firstSize = firstRefunds.size();
		assertNotNull(firstRefunds);
		assertTrue(firstSize <= limit);
		Transaction transaction= createTransaction();
		TransactionService transactionService=Paymill.getService(TransactionService.class);
		transactionService.refund(transaction, 1);
		List<Refund> secondRefunds = srv.list(0, limit);
		int secondSize = secondRefunds.size();
		assertNotNull(secondRefunds);
		assertTrue(secondSize > 0 && secondSize <= limit);
		assertTrue(secondSize == limit || secondSize == firstSize + 1);
	}
}

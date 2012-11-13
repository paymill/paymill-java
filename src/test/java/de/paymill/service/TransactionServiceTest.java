package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Refund;
import de.paymill.model.Refund.Status;
import de.paymill.model.Transaction;
import de.paymill.net.ApiException;
import de.paymill.net.Filter;

public class TransactionServiceTest extends TestCase {

	@Test
	public void testGetNonExistingTransaction() {
		TransactionService srv = Paymill.getService(TransactionService.class);

		try {
			srv.get("idontexist");
			fail("Expected exception");
		} catch (ApiException ex) {
			assertEquals("transaction_not_found", ex.getCode());
		}
	}

	@Test
	public void testCreateTransaction() {
		String token = getToken();
		TransactionService srv = Paymill.getService(TransactionService.class);
		Transaction params = new Transaction();
		params.setToken(token);
		params.setAmount(399);
		params.setCurrency("EUR");
		Transaction transaction = srv.create(params);
		assertNotNull(transaction.getId());
		assertNull(transaction.getToken());
		assertEquals(399, (int) transaction.getAmount());
		assertNotNull(transaction.getPayment());
		assertNotNull(transaction.getPayment().getId());
	}

	@Test
	public void testListTransactions() {
		TransactionService srv = Paymill.getService(TransactionService.class);
		List<Transaction> list = srv.list(0, 10);

		assertTrue(list.size() > 0);
		assertTrue(list.get(0) instanceof Transaction);
	}

	@Test
	public void testGetTransaction() {
		TransactionService srv = Paymill.getService(TransactionService.class);
		List<Transaction> list = srv.list(0, 1);
		Transaction tx = srv.get(list.get(0).getId());

		assertNotNull(tx);
		assertNotNull(tx.getId());
	}

	@Test
	public void testRefund() {
		TransactionService srv = Paymill.getService(TransactionService.class);
		Filter filter = new Filter();
		filter.add("amount", ">100");
		List<Transaction> list = srv.list(0, 1, filter);

		assertEquals(1, list.size());

		Transaction tx = list.get(0);
		Refund refund = srv.refund(tx, 50);

		assertNotNull(refund);
		assertEquals(Status.REFUNDED, refund.getStatus());
		assertEquals(50, (int) refund.getAmount());
		assertNotNull(refund.getId());

		int amount = tx.getAmount();
		tx = srv.get(tx.getId());
		assertEquals(Transaction.Status.PARTIAL_REFUNDED, tx.getStatus());
		assertEquals(amount - 50, (int) tx.getAmount());

		srv.refund(tx, tx.getAmount());

		tx = srv.get(tx.getId());
		assertEquals(Transaction.Status.REFUNDED, tx.getStatus());
		assertEquals(0, (int) tx.getAmount());
	}
}

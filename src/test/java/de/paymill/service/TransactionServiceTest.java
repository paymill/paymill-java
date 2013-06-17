package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Fee;
import de.paymill.model.Payment;
import de.paymill.model.Preauthorization;
import de.paymill.model.Refund;
import de.paymill.model.Refund.Status;
import de.paymill.model.Transaction;
import de.paymill.net.ApiException;
import de.paymill.net.HttpClient;

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
		String token = getToken();
		TransactionService srv = Paymill.getService(TransactionService.class);
		Transaction params = new Transaction();
		params.setToken(token);
		params.setAmount(399);
		params.setCurrency("EUR");
		Transaction tx = srv.create(params);

		Refund refund = srv.refund(tx, 50);

		assertNotNull(refund);
		assertEquals(Status.REFUNDED, refund.getStatus());
		assertEquals(50, (int) refund.getAmount());
		assertNotNull(refund.getId());

		int amount = tx.getAmount();
		tx = srv.get(tx.getId());
		assertEquals(amount - 50, (int) tx.getAmount());
		assertEquals(Transaction.Status.PARTIAL_REFUNDED, tx.getStatus());

		srv.refund(tx, tx.getAmount());

		tx = srv.get(tx.getId());
		assertEquals(Transaction.Status.REFUNDED, tx.getStatus());
		assertEquals(0, (int) tx.getAmount());
	}

	@Test
	public void testCreateWithPayment() {
		PaymentService srvPayment = Paymill.getService(PaymentService.class);
		Payment payment = srvPayment.create(getToken());

		TransactionService svrTx = Paymill.getService(TransactionService.class);
		Transaction transactionParams = new Transaction();
		transactionParams.setPayment(payment);
		transactionParams.setAmount(100);
		transactionParams.setCurrency("EUR");
		Transaction tx = svrTx.create(transactionParams);

		assertNotNull(tx);
		assertNotNull(tx.getId());
		assertEquals((int) tx.getAmount(), 100);
		assertNotNull(tx.getPayment());
		assertEquals(payment.getId(), tx.getPayment().getId());
	}

	@Test
	public void testCreateWithPreauthorization() {
		PreauthorizationService srvPreauthorization = Paymill
				.getService(PreauthorizationService.class);
		Preauthorization params = new Preauthorization();
		params.setToken(getToken());
		params.setAmount(100);
		params.setCurrency("EUR");
		Preauthorization preauthorization = srvPreauthorization.create(params);

		TransactionService svrTx = Paymill.getService(TransactionService.class);
		Transaction transactionParams = new Transaction();
		transactionParams.setPreauthorization(preauthorization);
		transactionParams.setAmount(100);
		transactionParams.setCurrency("EUR");
		transactionParams.setDescription("Preauth TRX test");
		Transaction tx = svrTx.create(transactionParams);

		assertNotNull(tx);
		assertNotNull(tx.getId());
		assertEquals((int) tx.getAmount(), 100);
		assertNotNull(tx.getPayment());
		assertEquals(preauthorization.getId(), tx.getPreauthorization().getId());
	}
	
	@Test
	public void testFailedTransaction()
	{
		String token = getToken("5555555555554444", "123", "12", "2016");
		TransactionService srv = Paymill.getService(TransactionService.class);
		
		Transaction params = new Transaction();
		params.setToken(token);
		params.setAmount(199);
		params.setCurrency("EUR");
		
		Transaction transaction = srv.create(params);
		assertEquals(Transaction.Status.FAILED, transaction.getStatus());
		assertEquals(199, (int)transaction.getAmount());
	}
	
	@Test
	public void testGetTransactionWithFees() throws Exception
	{
		HttpClient client = Paymill.getClient();
		InputStream stream = new FileInputStream(new File("test/fee.json"));
		Transaction tx = client.decode(stream, Transaction.class);
		List<Fee> fees = tx.getFees();
		
		assertNotNull(fees);
		Fee fee = fees.get(0);
		assertNotNull(fee);
		
		assertEquals(Fee.Type.APPLICATION, fee.getType());
		assertEquals("app_166174a508fe0078605b83f88545f1c5da430d430", fee.getApplication());
		assertEquals(Integer.valueOf(10), fee.getAmount());
	}
}

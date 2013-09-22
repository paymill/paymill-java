package de.paymill;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.paymill.model.Client;
import de.paymill.model.Preauthorization;
import de.paymill.model.Transaction;
import de.paymill.service.ClientService;
import de.paymill.service.PreauthorizationService;
import de.paymill.service.TransactionService;

public class RegressionTests extends TestCase {

	/**
	 * https://github.com/paymill/paymill-java/issues/23
	 */
	@Test
	public void testIssue23() {
		// services
		ClientService clientService = Paymill.getService(ClientService.class);
		PreauthorizationService preauthorizationService = Paymill.getService(PreauthorizationService.class);
		TransactionService transactionService = Paymill.getService(TransactionService.class);
		// create a client
		final String email = "m@m.com";
		Client clientParams = new Client();
		clientParams.setEmail(email);
		Client client = clientService.create(clientParams);
		// create a preauth with client and check
		Preauthorization preauthParams = new Preauthorization();
		preauthParams.setToken(getToken());
		preauthParams.setAmount(399);
		preauthParams.setCurrency("EUR");
		preauthParams.setClient(client);
		Preauthorization preauthorization = preauthorizationService.create(preauthParams);
		assertNotNull(preauthorization.getId());
		assertNull(preauthorization.getToken());
		assertEquals(399, (int) preauthorization.getAmount());
		assertNotNull(preauthorization.getPayment());
		assertNotNull(preauthorization.getPayment().getId());
		// check if client is ok...
		assertNotNull(preauthorization.getClient());
		assertNotNull(preauthorization.getClient().getId());
		assertEquals(client.getId(), preauthorization.getClient().getId());
		assertEquals(email, preauthorization.getClient().getEmail());

		// create a transaction with client and check
		Transaction transactionParams = new Transaction();
		transactionParams.setToken(getToken());
		transactionParams.setAmount(399);
		transactionParams.setCurrency("EUR");
		transactionParams.setClient(client);
		Transaction transaction = transactionService.create(transactionParams);
		assertNotNull(transaction.getId());
		assertNull(transaction.getToken());
		assertEquals(399, (int) transaction.getAmount());
		assertNotNull(transaction.getPayment());
		assertNotNull(transaction.getPayment().getId());
		// check if client is ok...
		assertNotNull(transaction.getClient());
		assertNotNull(transaction.getClient().getId());
		assertEquals(client.getId(), transaction.getClient().getId());
		assertEquals(email, transaction.getClient().getEmail());
	}
}

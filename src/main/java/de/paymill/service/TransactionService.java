package de.paymill.service;

import de.paymill.Paymill;
import de.paymill.model.Refund;
import de.paymill.model.Transaction;
import de.paymill.net.HttpClient;

public class TransactionService extends AbstractService<Transaction> {

	public TransactionService() {
		this(Paymill.getClient());
	}

	public TransactionService(HttpClient client) {
		super("transactions", Transaction.class, client);
	}

	public Refund refund(Transaction tx, int amount) {
		return refund(tx.getId(), amount, null);
	}

	public Refund refund(Transaction tx, int amount, String description) {
		return refund(tx.getId(), amount, description);
	}

	public Refund refund(String tx, int amount) {
		return refund(tx, amount, null);
	}

	public Refund refund(String tx, int amount, String description) {
		Refund refund = new Refund();
		refund.setAmount(amount);
		refund.setDescription(description);
		return client.post("refunds/" + tx, refund, Refund.class);
	}
}

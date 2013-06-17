package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.Paymill;
import de.paymill.model.Fee;
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
	
	/**
	 * Create a transaction and collect the application fee.
	 * @param tx
	 * @param fee
	 * @return
	 */
	public Transaction create(Transaction tx, Fee fee)
	{
		if (tx.getPayment() == null && tx.getToken() == null) {
			throw new NullPointerException("payment, token");
		}
		if (tx.getPayment() != null && tx.getClient() == null) {
			throw new NullPointerException("client");
		}
		if (fee.getType() != Fee.Type.APPLICATION) {
			throw new IllegalArgumentException("Fee type must be 'application'");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", tx.getAmount());
		params.put("currency", tx.getCurrency());
		params.put("fee_amount", fee.getAmount());
		params.put("fee_payment", fee.getPayment());

		if (tx.getPayment() != null) {
			params.put("payment", tx.getPayment().getId());
			params.put("client", tx.getClient().getId());
		} else {
			params.put("token", tx.getToken());
		}
		
		return client.post(resource, params, Transaction.class);
	}
}

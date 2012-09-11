package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.model.Refund;
import de.paymill.model.Transaction;
import de.paymill.net.HttpClient;

public class RefundService extends AbstractService<Refund> {

	public RefundService() {
		super("refunds", Refund.class);
	}

	public RefundService(HttpClient client) {
		super("refunds", Refund.class, client);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param obj
	 * @return
	 */
	public Refund create(Refund obj) {
		Transaction tx = obj.getTransaction();
		if (tx == null)
			throw new NullPointerException("transaction");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", obj.getAmount());
		return client.post(resource + "/" + tx.getId(), params, modelClass);
	}

}

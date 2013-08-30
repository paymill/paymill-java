/**
 * 
 */
package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.Paymill;
import de.paymill.model.Client;
import de.paymill.model.Payment;
import de.paymill.net.HttpClient;

/**
 * @author jk
 * 
 */
public class PaymentService extends AbstractService<Payment> {

	public PaymentService() {
		this(Paymill.getClient());
	}

	public PaymentService(HttpClient client) {
		super("payments", Payment.class, client);
	}
	@Override
	public Payment create(Payment obj) {
		// payments cannot be created directly, only with token
		throw new IllegalArgumentException("Payments can be created only with a token");
	}
	public Payment create(String token) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", token);
		return client.post(resource, params, modelClass);
	}

	public Payment create(String token, Client client) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", token);
		params.put("client", client.getId());
		return this.client.post(resource, params, modelClass);
	}

}

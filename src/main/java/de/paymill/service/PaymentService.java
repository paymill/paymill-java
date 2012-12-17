/**
 * 
 */
package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.model.Client;
import de.paymill.model.Payment;
import de.paymill.net.HttpClient;

/**
 * @author jk
 * 
 */
public class PaymentService extends AbstractService<Payment> {

	public PaymentService() {
		super("payments", Payment.class);
	}

	public PaymentService(HttpClient client) {
		super("payments", Payment.class, client);
	}

	public Payment create(String token) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", token);
		return client.post(resource, params, modelClass);
	}

	// Documented here, but missing
	// https://www.paymill.com/it-it/documentation-3/reference/api-reference/index.html#create-new-credit-card-payment-with
	// Not having this method, will cause problem during subscription management (eg, duplicate clients).
    public Payment create(String token, Client client) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("token", token);
        params.put("client", client.getId());
        return this.client.post(resource, params, modelClass);
    }

}

package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.Paymill;
import de.paymill.model.Preauthorization;
import de.paymill.model.Transaction;
import de.paymill.net.HttpClient;

public class PreauthorizationService extends AbstractService<Preauthorization> {

	public PreauthorizationService() {
		this(Paymill.getClient());
	}

	public PreauthorizationService(HttpClient client) {
		super("preauthorizations", Preauthorization.class, client);
	}
	
	public Preauthorization create(Preauthorization preauth) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("amount", preauth.getAmount());
		params.put("currency", preauth.getCurrency());
		
		if (preauth.getPayment() != null) {
			params.put("payment", preauth.getPayment().getId());
		} else {
			params.put("token", preauth.getToken());
		}
		
		Transaction tx = client.post(resource, params, Transaction.class);
		return tx.getPreauthorization();
	}
}
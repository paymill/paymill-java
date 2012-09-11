package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.model.Client;
import de.paymill.model.Offer;
import de.paymill.model.Subscription;
import de.paymill.net.HttpClient;

public class SubscriptionService extends AbstractService<Subscription> {

	public SubscriptionService() {
		super("Subscriptions", Subscription.class);
	}

	public SubscriptionService(HttpClient client) {
		super("Subscriptions", Subscription.class, client);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param obj
	 * @return
	 */
	public Subscription create(Subscription obj) {
		Offer offer   = obj.getOffer();
		Client client = obj.getCustomer();
		if (offer == null)  throw new NullPointerException("offer");
		if (client == null) throw new NullPointerException("client");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offer", offer.getId());
		params.put("client", client.getId());
		
		return this.client.post(resource, params, modelClass);
	}
}

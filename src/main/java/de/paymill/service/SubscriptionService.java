package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.Paymill;
import de.paymill.model.Client;
import de.paymill.model.Offer;
import de.paymill.model.Payment;
import de.paymill.model.Subscription;
import de.paymill.net.HttpClient;

public class SubscriptionService extends AbstractService<Subscription> {

	public SubscriptionService() {
		this(Paymill.getClient());
	}

	public SubscriptionService(HttpClient client) {
		super("Subscriptions", Subscription.class, client);
		this.updateableProperties.add("offer");
		this.updateableProperties.add("payment");
		this.updateableProperties.add("cancelAtPeriodEnd");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param obj
	 * @return
	 */
	public Subscription create(Subscription obj) {
		Offer offer   = obj.getOffer();
		Client client = obj.getClient();
		Payment pay   = obj.getPayment();
		if (offer == null)  throw new NullPointerException("offer");
		if (client == null) throw new NullPointerException("client");
		if (pay == null)    throw new NullPointerException("payment");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offer", offer.getId());
		params.put("client", client.getId());
		params.put("payment", pay.getId());
		
		return this.client.post(resource, params, modelClass);
	}
}

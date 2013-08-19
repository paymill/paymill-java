/**
 * 
 */
package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.Paymill;
import de.paymill.model.Client;
import de.paymill.model.Offer;
import de.paymill.model.Payment;
import de.paymill.model.Subscription;
import de.paymill.net.HttpClient;

public class OfferService extends AbstractService<Offer> {

	public OfferService() {
		this(Paymill.getClient());
	}

	public OfferService(HttpClient client) {
		super("offers", Offer.class, client);
		this.updateableProperties.add("name");
		this.updateableProperties.add("amount");
		this.updateableProperties.add("interval");
		this.updateableProperties.add("trialPeriodDays");
	}

	/**
	 * Subscribes a client to an offer and returns the newly created
	 * subscription object.
	 * 
	 * @param offer
	 * @param client
	 * @param payment
	 * @return
	 */
	public Subscription subscribe(Offer offer, Client client, Payment payment) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offer", offer.getId());
		params.put("client", client.getId());
		params.put("payment", payment.getId());

		return this.client.post("subscriptions", params, Subscription.class);
	}

	/**
	 * Removes a subscription from a client.
	 * 
	 * @param subscription
	 */
	public void unsubscribe(Subscription subscription) {
		client.delete("subscriptions", subscription.getId());
	}
}

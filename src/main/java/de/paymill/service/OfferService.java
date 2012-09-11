/**
 * 
 */
package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.model.Client;
import de.paymill.model.Offer;
import de.paymill.model.Subscription;
import de.paymill.net.HttpClient;

public class OfferService extends AbstractService<Offer> {

	public OfferService() {
		super("offers", Offer.class);
	}

	public OfferService(HttpClient client) {
		super("offers", Offer.class, client);
	}

	/**
	 * Subscribes a client to an offer and returns the newly created
	 * subscription object.
	 * 
	 * @param offer
	 * @param client
	 * @return
	 */
	public Subscription subscribe(Offer offer, Client client) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offer", offer.getId());
		params.put("client", client.getId());

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

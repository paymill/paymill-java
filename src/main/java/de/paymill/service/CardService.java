/**
 * 
 */
package de.paymill.service;

import java.util.HashMap;
import java.util.Map;

import de.paymill.model.Card;
import de.paymill.net.HttpClient;

/**
 * @author jk
 * 
 */
public class CardService extends AbstractService<Card> {

	public CardService() {
		super("creditcards", Card.class);
	}

	public CardService(HttpClient client) {
		super("creditcards", Card.class, client);
	}

	public Card create(String token) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", token);
		return client.post(resource, params, modelClass);
	}

}

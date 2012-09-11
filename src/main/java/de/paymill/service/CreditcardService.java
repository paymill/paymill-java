/**
 * 
 */
package de.paymill.service;

import de.paymill.model.Card0;
import de.paymill.net.HttpClient;

/**
 * @author jk
 * 
 */
public class CreditcardService extends AbstractService<Card0> {

	public CreditcardService() {
		super("creditcard", Card0.class);
	}

	public CreditcardService(HttpClient client) {
		super("creditcard", Card0.class, client);
	}

}

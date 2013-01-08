package de.paymill.service;

import de.paymill.Paymill;
import de.paymill.model.Client;
import de.paymill.net.HttpClient;

public class ClientService extends AbstractService<Client> {

	public ClientService() {
		this(Paymill.getClient());
	}

	public ClientService(HttpClient client) {
		super("clients", Client.class, client);
		this.updateableProperties.add("email");
		this.updateableProperties.add("description");
	}

}

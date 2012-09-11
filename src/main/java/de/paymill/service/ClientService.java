package de.paymill.service;

import de.paymill.model.Client;
import de.paymill.net.HttpClient;

public class ClientService extends AbstractService<Client> {

	public ClientService() {
		super("clients", Client.class);
	}

	public ClientService(HttpClient client) {
		super("clients", Client.class, client);
	}

}

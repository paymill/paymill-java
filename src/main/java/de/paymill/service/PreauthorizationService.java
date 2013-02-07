package de.paymill.service;

import de.paymill.Paymill;
import de.paymill.model.Preauthorization;
import de.paymill.net.HttpClient;

public class PreauthorizationService extends AbstractService<Preauthorization> {

	public PreauthorizationService() {
		this(Paymill.getClient());
	}

	public PreauthorizationService(HttpClient client) {
		super("preauthorizations", Preauthorization.class, client);
	}
}
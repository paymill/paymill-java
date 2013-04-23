package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Client;

public class ClientServiceTest extends TestCase {

	@Test
	public void testCreateClient() {
		ClientService srv = Paymill.getService(ClientService.class);
		String email = getRandomEmail();
		Client params = new Client();
		params.setEmail(email);

		Client client = srv.create(params);
		assertEquals(email, client.getEmail());
		assertNotNull(client.getId());
	}

	@Test
	public void testGetClient() {
		ClientService srv = Paymill.getService(ClientService.class);
		Client params = new Client();
		params.setEmail(getRandomEmail());
		Client client1 = srv.create(params);
		Client client2 = srv.get(client1.getId());

		assertNotNull(client2.getId());
		assertEquals(client1.getId(), client2.getId());
	}

	@Test
	public void testGetClientList() {
		ClientService srv = Paymill.getService(ClientService.class);
		List<Client> clients = srv.list(0, 5);

		assertNotNull(clients);
		assertEquals(5, clients.size());
	}
	
	@Test
	public void testUpdateClient() {
		ClientService srv = Paymill.getService(ClientService.class);
		String email = getRandomEmail();
		Client params = new Client();
		params.setEmail(email);

		Client client = srv.create(params);
		assertNull(client.getDescription());
		
		client.setDescription("Description");
		client = srv.update(client);
		
		assertEquals("Description", client.getDescription());
	}
}

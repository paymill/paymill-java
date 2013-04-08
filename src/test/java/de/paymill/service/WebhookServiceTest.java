package de.paymill.service;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Event;
import de.paymill.model.Webhook;
import de.paymill.model.Webhook.EventType;

public class WebhookServiceTest extends TestCase {
	@Test
	public void testCreateURLWebhook() {
		Paymill.setApiKey(getToken());
		WebhookService srv = Paymill.getService(WebhookService.class);
		URL url = getWebhookUrl();
		Webhook webhook = srv.create(url, EventType.SUBSCRIPTION_SUCCEEDED,
				EventType.SUBSCRIPTION_FAILED);
		assertNotNull(webhook);
	}

	@Test
	public void testCreateEmailWebhook() {
		Paymill.setApiKey(getToken());		
		WebhookService srv = Paymill.getService(WebhookService.class);
		String email = getRandomEmail();
		Webhook webhook = srv.create(email, EventType.SUBSCRIPTION_SUCCEEDED,
				EventType.SUBSCRIPTION_FAILED);
		assertNotNull(webhook);
	}

	@Test
	public void bugParseEvent() {
		try {
			Paymill.setApiKey("dummy");
			WebhookService srv = Paymill.getService(WebhookService.class);
			Event event = srv.parse(new FileInputStream(new File("test/offerbug.js")));
			assertNotNull(event);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Test
	public void testParseEvent() {
		try {
			Paymill.setApiKey("dummy");
			WebhookService srv = Paymill.getService(WebhookService.class);
			Event event = srv.parse(new FileInputStream(new File("test/failed.js")));
			assertNotNull(event);
			event = srv.parse(new FileInputStream(new File("test/success.js")));
			assertNotNull(event);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
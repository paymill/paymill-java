package de.paymill.service;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Webhook;
import de.paymill.model.Webhook.EventType;

public class WebhookServiceTest extends TestCase {
	@Test
	public void testCreateWebhook() {
		WebhookService srv = Paymill.getService(WebhookService.class);
		URL url = getWebhookUrl();
		Webhook webhook = srv.create(url, EventType.SUBSCRIPITON_SUCCEEDED,
				EventType.SUBSCRIPITON_FAILED);
		assertNotNull(webhook);
	}
}
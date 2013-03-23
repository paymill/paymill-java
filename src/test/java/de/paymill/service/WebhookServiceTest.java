package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Event;
import de.paymill.model.Webhook;
import de.paymill.model.Webhook.EventType;
import de.paymill.net.Filter;

public class WebhookServiceTest extends TestCase {
	@Test
	public void testCreateUrlWebhook() {
		WebhookService srv = Paymill.getService(WebhookService.class);
		URL url = getWebhookUrl();
		Webhook webhook = srv.createUrl(
                   url, 
                   EventType.SUBSCRIPTION_SUCCEEDED, 
		   EventType.SUBSCRIPTION_FAILED);
		assertNotNull(webhook);
	}

	@Test
	public void testCreateEmailWebhook() {
            EventType[] eventTypes = { EventType.SUBSCRIPTION_SUCCEEDED, EventType.SUBSCRIPTION_FAILED }; 
            WebhookService srv = Paymill.getService(WebhookService.class);
            Webhook webhook = new Webhook();
            webhook.setEmail(getWebhookEmail());
            webhook.setEventTypes(eventTypes);
            try {
                webhook = srv.create(webhook);
                assertNotNull(webhook);
            } catch(Exception e) {
                fail("Create e-mail webhook failed!");
            }
	}

	@Test
	public void testWebhookDetails() {
            WebhookService srv = Paymill.getService(WebhookService.class);
            URL url = getWebhookUrl();
            Webhook webhook = srv.createUrl(
               url, 
               EventType.SUBSCRIPTION_SUCCEEDED, 
               EventType.SUBSCRIPTION_FAILED);
            
            String id = webhook.getId();
            
            webhook = srv.get(id);

            assertNotNull(webhook);
            assertNotNull(webhook.getId());
	}

	@Test
	public void testUpdateWebhook() {
            String email = getWebhookEmail();
            WebhookService srv = Paymill.getService(WebhookService.class);
            List<Webhook> list = srv.list(0, 1, "created_at_desc");
            Webhook webhook = list.get(0);
            
            try {
                webhook.setEmail(email);
                webhook.setUrl(null);
                webhook = srv.update(webhook);
                assertNotNull(webhook);
                assertNotNull(webhook.getId());
                assertEquals(email, webhook.getEmail());
            } catch(Exception e) {
                fail("Update failed!");
            }
	}

	@Test
	public void testRemoveWebhook() {
		WebhookService srv = Paymill.getService(WebhookService.class);
                
                Filter urlFilter = new Filter();
                urlFilter.add("url", getWebhookUrl().toString());                
		List<Webhook> urlList = srv.list(urlFilter);
                for(Webhook webhook: urlList) {
                    srv.delete(webhook.getId());
                }
                
                Filter emailFilter = new Filter();
                emailFilter.add("email", getWebhookEmail());
 		List<Webhook> emailList = srv.list(emailFilter);
                for(Webhook webhook: emailList) {
                    srv.delete(webhook.getId());
                }
 	}

	@Test
	public void bugParseEvent() {
		String oldKey = Paymill.getApiKey();
		try {
			Paymill.setApiKey("dummy");
			WebhookService srv = Paymill.getService(WebhookService.class);
			Event event = srv.parse(new FileInputStream(new File("test/offerbug.js")));
			assertNotNull(event);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Paymill.setApiKey(oldKey);
		}
	}
	@Test
	public void testParseEvent() {
		String oldKey = Paymill.getApiKey();
		try {
			Paymill.setApiKey("dummy");
			WebhookService srv = Paymill.getService(WebhookService.class);
			Event event = srv.parse(new FileInputStream(new File("test/failed.js")));
			assertNotNull(event);
			event = srv.parse(new FileInputStream(new File("test/success.js")));
			assertNotNull(event);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Paymill.setApiKey(oldKey);
		}
	}
}
/**
 * 
 */
package de.paymill.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.paymill.Paymill;
import de.paymill.PaymillException;
import de.paymill.model.Event;
import de.paymill.model.Webhook;
import de.paymill.model.Webhook.EventType;
import de.paymill.net.HttpClient;

/**
 * @author df
 * 
 */
public class WebhookService extends AbstractService<Webhook> {
	public WebhookService() {
		this(Paymill.getClient());
	}

	public WebhookService(HttpClient client) {
		super("webhooks", Webhook.class, client);
	}

	public Webhook create(Webhook obj) {
		return create(obj.getUrl(), obj.getEventTypes());
	}

	/**
	 * @param url the callback url that this webhook will call
	 * @param eventTypes the event types to subscribe 
	 * @return the generated webhook
	 */
	public Webhook create(URL url, EventType... eventTypes) {
		if (url == null)
			throw new NullPointerException("url");
		if (eventTypes == null)
			throw new NullPointerException("eventTypes");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("url", url.toString());
		params.put("event_types", eventTypes);
		return client.post(resource, params, modelClass);
	}
	
	public Event parse(InputStream inputStream) {
		try {
			return client.decode(inputStream, Event.class);
		} catch (IOException e) {
			throw new PaymillException("Failed to decode webhook event object from callback");
		}
	}
}
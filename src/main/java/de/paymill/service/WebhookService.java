/**
 * 
 */
package de.paymill.service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.paymill.Paymill;
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
		boolean first = true;
		StringBuilder eventTypeBuilder = new StringBuilder();
		for (EventType eventType : eventTypes) {
			if (!first)
				eventTypeBuilder.append(',');
			eventTypeBuilder.append(eventType.toString());
			first = false;
		}
		return create(url, eventTypeBuilder.toString());
	}

	/**
	 * @param url the callback url that this webhook will call
	 * @param eventTypes the event types (comma separated list) to subscribe 
	 * @return the generated webhook
	 */
	public Webhook create(URL url, String eventTypes) {
		if (url == null)
			throw new NullPointerException("url");
		if (eventTypes == null)
			throw new NullPointerException("eventTypes");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("url", url.toString());
		params.put("event_types", eventTypes);
		return client.post(resource, params, modelClass);
	}
}

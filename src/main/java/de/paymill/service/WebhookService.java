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

	@Override
	public Webhook create(Webhook obj) {
		if (obj.getUrl() == null) {
			return createEmail(obj.getEmail(), obj.getEventTypes());
		}

		return createUrl(obj.getUrl(), obj.getEventTypes());
	}

	/**
	 * @param url
	 *            the callback url that this webhook will call
	 * @param eventTypes
	 *            the event types to subscribe
	 * @return the generated webhook
	 */
	public Webhook createUrl(URL url, EventType... eventTypes) {
		if (url == null)
			throw new NullPointerException("url");
		if (eventTypes == null)
			throw new NullPointerException("eventTypes");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("url", url.toString());
		params.put("event_types", eventTypes);
		return client.post(resource, params, modelClass);
	}

	/**
	 * @param email
	 *            the callback url that this webhook will call
	 * @param eventTypes
	 *            the event types to subscribe
	 * @return the generated webhook
	 */
	public Webhook createEmail(String email, EventType... eventTypes) {
		if (email == null)
			throw new NullPointerException("email");
		if (eventTypes == null)
			throw new NullPointerException("eventTypes");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("event_types", eventTypes);
		return client.post(resource, params, modelClass);
	}

	@Override
	public Webhook update(Webhook obj) {
		Map<String, Object> params = new HashMap<String, Object>();
		String url = obj.getUrl() == null ? null : obj.getUrl().toString();
		String email = obj.getEmail() == null ? null : obj.getEmail();
		if (url != null) {
			params.put("url", url);
		}
		if (email != null) {
			params.put("email", email);
		}
		params.put("event_types", obj.getEventTypes());
		return client.put(resource, obj.getId(), params, modelClass);
	}

	public Event parse(InputStream inputStream) {
		try {
			return client.decode(inputStream, Event.class);
		} catch (IOException e) {
			throw new PaymillException(
					"Failed to decode webhook event object from callback");
		}
	}
}
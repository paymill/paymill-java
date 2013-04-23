package de.paymill.model;

import java.net.URL;
import java.util.Date;

public class Webhook implements IPaymillObject {
	public enum EventType {
		CHARGEBACK_EXECUTED, REFUND_SUCCEEDED, REFUND_FAILED, SUBSCRIPTION_SUCCEEDED, SUBSCRIPTION_FAILED, TRANSACTION_SUCCEEDED, TRANSACTION_FAILED;
	}

	private String id;
	private URL url;
	private String email;
	private Date createdAt;
	private Date updatedAt;
	private EventType[] eventTypes;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the callback-url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the callback-url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * @return the callback-email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the callback-email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @param eventTypes
	 *            the eventTypes to subscribe
	 */
	public void setEventTypes(EventType[] eventTypes) {
		this.eventTypes = eventTypes;
	}

	/**
	 * @return the event types
	 */
	public EventType[] getEventTypes() {
		return eventTypes;
	}
}
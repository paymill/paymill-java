package de.paymill.model;

import java.net.URL;
import java.util.Date;

public class Webhook implements IPaymillObject {
	public enum EventType {
		CHARGEBACK_EXECUTED("chargeback.executed"), REFUND_SUCCEEDED(
				"refund.succeeded"), REFUND_FAILED("refund.failed"), SUBSCRIPITON_SUCCEEDED(
				"subscription.succeeded"), SUBSCRIPITON_FAILED(
				"subscription.failed"), TRANSACTION_SUCCEEDED(
				"transaction.succeeded"), TRANSACTION_FAILED(
				"transaction.failed");

		private String key;

		public static EventType find(String name) {
			for (EventType eventType : values()) {
				if (eventType.toString().equals(name)) {
					return eventType;
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return key;
		}

		private EventType(String key) {
			this.key = key;
		}

	}

	private String id;
	private URL url;
	private Date createdAt;
	private Date updatedAt;
	private String eventTypes;

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
	public void setEventTypes(String eventTypes) {
		this.eventTypes = eventTypes;
	}

	/**
	 * @return the event types
	 */
	public String getEventTypes() {
		return eventTypes;
	}
}
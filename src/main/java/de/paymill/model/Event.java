package de.paymill.model;

import java.util.Date;

import de.paymill.model.Webhook.EventType;

public class Event {
	public static class EventResource extends Transaction {
		private Subscription subscription;
		private Transaction transaction;
		
		public Subscription getSubscription() {
			return subscription;
		}
		public Transaction getTransaction() {
			return transaction;
		}
	}
	
	private EventType eventType;
	private EventResource eventResource;
	private Date createdAt; 

	public EventType getEventType() {
		return eventType;
	}

	public EventResource getEventResource() {
		return eventResource;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

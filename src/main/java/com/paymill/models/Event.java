package com.paymill.models;

import java.util.Date;

import com.paymill.models.Webhook.EventType;

//TODO[VNi]: Why do I have this class
public class Event {
  public static class EventResource extends Transaction {
    private Subscription subscription;
    private Transaction  transaction;

    public Subscription getSubscription() {
      return subscription;
    }

    public Transaction getTransaction() {
      return transaction;
    }
  }

  private EventType     eventType;
  private EventResource eventResource;
  private Date          createdAt;

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

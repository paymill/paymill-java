package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.PaymillList;
import com.paymill.models.Webhook;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class WebhookService extends AbstractService {

  private final static String PATH = "/webhooks";

  private WebhookService( Client httpClient ) {
    super( httpClient );
  }

  public PaymillList<Webhook> list() {
    return this.list( null, null );
  }

  public PaymillList<Webhook> list( Webhook.Filter filter, Webhook.Order order ) {
    return RestfulUtils.list( WebhookService.PATH, filter, order, Webhook.class, super.httpClient );
  }

  public Webhook get( Webhook webhook ) {
    return RestfulUtils.show( WebhookService.PATH, RestfulUtils.getIdByReflection( webhook ), Webhook.class, super.httpClient );
  }

  public Webhook get( String webhookId ) {
    return RestfulUtils.show( WebhookService.PATH, webhookId, Webhook.class, super.httpClient );
  }

  public Webhook createUrlWebhook( String url, Webhook.EventType[] eventTypes ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "url", url );

    for( Webhook.EventType eventType : eventTypes )
      params.add( "event_types[]", eventType.getValue() );

    return RestfulUtils.create( WebhookService.PATH, params, Webhook.class, super.httpClient );
  }

  public Webhook createUrlWebhook( String url, String[] eventTypes ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "url", url );

    for( String eventType : eventTypes )
      params.add( "event_types[]", eventType );

    return RestfulUtils.create( WebhookService.PATH, params, Webhook.class, super.httpClient );
  }

  public Webhook createEmailWebhook( String email, Webhook.EventType[] eventTypes ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "email", email );

    for( Webhook.EventType eventType : eventTypes )
      params.add( "event_types[]", eventType.getValue() );

    return RestfulUtils.create( WebhookService.PATH, params, Webhook.class, super.httpClient );
  }

  public Webhook createEmailWebhook( String email, String[] eventTypes ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "email", email );

    for( String eventType : eventTypes )
      params.add( "event_types[]", eventType );

    return RestfulUtils.create( WebhookService.PATH, params, Webhook.class, super.httpClient );
  }

  public Webhook update( Webhook webhook ) {
    return RestfulUtils.update( WebhookService.PATH, webhook, Webhook.class, super.httpClient );
  }

  public Webhook delete( Webhook webhook ) {
    RestfulUtils.delete( WebhookService.PATH, RestfulUtils.getIdByReflection( webhook ), Webhook.class, super.httpClient );
    webhook.setId( null );
    return webhook;
  }

  public Webhook delete( String webhookId ) {
    return this.delete( new Webhook( webhookId ) );
  }



}

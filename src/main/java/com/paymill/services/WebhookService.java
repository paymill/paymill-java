package com.paymill.services;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.paymill.models.PaymillList;
import com.paymill.models.Webhook;
import com.paymill.models.Webhook.EventType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The {@link WebhookService} is used to list, create, edit and update PAYMILL {@link Webhook}s.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
public class WebhookService extends AbstractService {

  private final static String PATH = "/webhooks";

  private WebhookService( Client httpClient ) {
    super( httpClient );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Webhook} objects.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Webhook}s and their total count.
   */
  public PaymillList<Webhook> list() {
    return this.list( null, null, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Webhook} objects, overriding the default count and offset.
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Webhook}s and their total count.
   */
  public PaymillList<Webhook> list( Integer count, Integer offset ) {
    return this.list( null, null, count, offset );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Webhook} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied.
   * @param filter
   *          {@link Webhook.Filter} or <code>null</code>
   * @param order
   *          {@link Webhook.Order} or <code>null</code>
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Webhook}s and their total count.
   */
  public PaymillList<Webhook> list( Webhook.Filter filter, Webhook.Order order ) {
    return this.list( filter, order, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Webhook} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied, overriding the default count and
   * offset.
   * @param filter
   *          {@link Webhook.Filter} or <code>null</code>
   * @param order
   *          {@link Webhook.Order} or <code>null</code>
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Webhook}s and their total count.
   */
  public PaymillList<Webhook> list( Webhook.Filter filter, Webhook.Order order, Integer count, Integer offset ) {
    return RestfulUtils.list( WebhookService.PATH, filter, order, count, offset, Webhook.class, super.httpClient );
  }

  /**
   * Returns and refresh data of a specific {@link Webhook}.
   * @param webhook
   *          A {@link Webhook} with Id.
   * @return Refreshed instance of the given {@link Webhook}.
   */
  public Webhook get( Webhook webhook ) {
    return RestfulUtils.show( WebhookService.PATH, webhook, Webhook.class, super.httpClient );
  }

  /**
   * Returns and refresh data of a specific {@link Webhook}.
   * @param webhookId
   *          A {@link Webhook} with Id.
   * @return Refreshed instance of the given {@link Webhook}.
   */
  public Webhook get( String webhookId ) {
    return this.get( new Webhook( webhookId ) );
  }

  /**
   * Creates a {@link Webhook}, which sends events to the given URL.
   * @param url
   *          The URL of the webhook.
   * @param eventTypes
   *          Includes a set of {@link Webhook} {@link EventType}s.
   * @return A {@link Webhook}
   */
  public Webhook createUrlWebhook( String url, Webhook.EventType[] eventTypes ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "url", url );

    for( Webhook.EventType eventType : eventTypes )
      params.add( "event_types[]", eventType.getValue() );

    return RestfulUtils.create( WebhookService.PATH, params, Webhook.class, super.httpClient );
  }

  /**
   * Creates a {@link Webhook}, which sends events to the given email.
   * @param email
   *          The {@link Webhook}s email. Must be a valid mail address.
   * @param eventTypes
   *          Includes a set of {@link Webhook} {@link EventType}s.
   * @return A {@link Webhook}
   */
  public Webhook createEmailWebhook( String email, Webhook.EventType[] eventTypes ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.add( "email", email );

    for( Webhook.EventType eventType : eventTypes )
      params.add( "event_types[]", eventType.getValue() );

    return RestfulUtils.create( WebhookService.PATH, params, Webhook.class, super.httpClient );
  }

  /**
   * Updates the {@link Webhook}. You can change the url/email and the event types.
   * @param webhook
   */
  public void update( Webhook webhook ) {
    RestfulUtils.update( WebhookService.PATH, webhook, Webhook.class, super.httpClient );
  }

  /**
   * All pending calls to a {@link Webhook} are deleted as well, as soon as you delete the {@link Webhook} itself.
   * @param webhook
   *          {@link Webhook} with existing Id.
   */
  public void delete( Webhook webhook ) {
    RestfulUtils.delete( WebhookService.PATH, webhook, Webhook.class, super.httpClient );
  }

  /**
   * All pending calls to a {@link Webhook} are deleted as well, as soon as you delete the {@link Webhook} itself.
   * @param webhookId
   *          The Id of an existing {@link Webhook}.
   */
  public void delete( String webhookId ) {
    this.delete( new Webhook( webhookId ) );
  }

}

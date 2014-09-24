package com.paymill.services;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Client;
import com.paymill.models.PaymillList;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The {@link ClientService} is used to list, create, edit, delete and update PAYMILL {@link Client}s.
 * @author Vassil Nikolov
 * @since 3.0.0
 */
public class ClientService extends AbstractService {

  private ClientService( com.sun.jersey.api.client.Client httpClient ) {
    super( httpClient );
  }

  private final static String PATH = "/clients";

  /**
   * This function returns a {@link List} of PAYMILL {@link Client} objects.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Client}s and their total count.
   */
  public PaymillList<Client> list() {
    return this.list( null, null, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Client} objects, overriding the default count and offset.
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Client}s and their total count.
   */
  public PaymillList<Client> list( Integer count, Integer offset ) {
    return this.list( null, null, count, offset );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Client} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied.
   * @param filter
   *          {@link Client.Filter} or <code>null</code>
   * @param order
   *          {@link Client.Order} or <code>null</code>
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Client}s and their total count.
   */
  public PaymillList<Client> list( Client.Filter filter, Client.Order order ) {
    return this.list( filter, order, null, null );
  }

  /**
   * This function returns a {@link List} of PAYMILL {@link Client} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied, overriding the default count and
   * offset.
   * @param filter
   *          {@link Client.Filter} or <code>null</code>
   * @param order
   *          {@link Client.Order} or <code>null</code>
   * @param count
   *          Max {@link Integer} of returned objects in the {@link PaymillList}
   * @param offset
   *          {@link Integer} to start from.
   * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Client}s and their total count.
   */
  public PaymillList<Client> list( Client.Filter filter, Client.Order order, Integer count, Integer offset ) {
    return RestfulUtils.list( ClientService.PATH, filter, order, count, offset, Client.class, super.httpClient );
  }

  /**
   * Get and refresh the details of an existing PAYMILL {@link Client}.
   * @param client
   *          A {@link Client} with Id.
   * @return Refreshed instance of the given {@link Client}.
   */
  public Client get( Client client ) {
    return RestfulUtils.show( ClientService.PATH, client, Client.class, super.httpClient );
  }

  /**
   * Get and refresh the details of an existing PAYMILL {@link Client}.
   * @param clientId
   *          Id of the {@link Client}
   * @return Refreshed instance of the given {@link Client}.
   */
  public Client get( String clientId ) {
    return this.get( new Client( clientId ) );
  }

  /**
   * Creates a {@link Client} object.
   * @return {@link Client} object, which represents a PAYMILL client.
   */
  public Client create() {
    return this.createWithEmailAndDescription( null, null );
  }

  /**
   * Creates a {@link Client} object with the given email.
   * @param email
   *          Mail address for the {@link Client}
   * @return {@link Client} object, which represents a PAYMILL client.
   */
  public Client createWithEmail( String email ) {
    return this.createWithEmailAndDescription( email, null );
  }

  /**
   * Creates a {@link Client} object with the given description.
   * @param description
   *          Description for the {@link Client}
   * @return {@link Client} object, which represents a PAYMILL client.
   */
  public Client createWithDescription( String description ) {
    return this.createWithEmailAndDescription( null, description );
  }

  /**
   * Creates a {@link Client} object.
   * @param email
   *          Mail address for the {@link Client} or <code>null</code>
   * @param description
   *          Description for the client or <code>null</code>
   * @return {@link Client} object, which represents a PAYMILL client.
   */
  public Client createWithEmailAndDescription( String email, String description ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    if( StringUtils.isNotBlank( email ) )
      params.add( "email", email );
    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( ClientService.PATH, params, Client.class, super.httpClient );
  }

  /**
   * This function updates the data of a client. To change only a specific attribute you can set this attribute in the update
   * request. All other attributes that should not be edited are not inserted. You can only edit the description, email and credit
   * card. The subscription can not be changed by updating the client data. This has to be done in the subscription call.
   * @param client
   *          A {@link Client} with Id.
   * @return {@link Client} object, which represents a PAYMILL client.
   */
  public void update( Client client ) {
    RestfulUtils.update( ClientService.PATH, client, Client.class, super.httpClient );
  }

  /**
   * This function deletes a client, but its transactions are not deleted.
   * @param client
   *          A {@link Client} with Id.
   * @return {@link Client} object without id, which represents a deleted PAYMILL client.
   */
  public void delete( Client client ) {
    RestfulUtils.delete( ClientService.PATH, client, Client.class, super.httpClient );
  }

  /**
   * This function deletes a client, but its transactions are not deleted.
   * @param clientId
   *          Id of the {@link Client}
   */
  public void delete( String clientId ) {
    this.get( new Client( clientId ) );
  }

}

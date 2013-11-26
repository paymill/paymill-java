package com.paymill.services;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Client;
import com.paymill.models.Client.Filter;
import com.paymill.models.Client.Order;
import com.paymill.models.PaymillList;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The {@link ClientService} is used to list, create, edit, delete and update PayMill {@link Client}s.
 * @author Vassil Nikolov
 */
public final class ClientService extends AbstractService {

  private ClientService( com.sun.jersey.api.client.Client httpClient ) {
    super( httpClient );
  }

  private final static String PATH = "/clients";

  /**
   * This function returns a {@link List} of PayMill {@link Client} objects.
   * @return {@link PaymillList} which contains a {@link List} of PayMill {@link Client}s their total count and the mode test or live.
   */
  public PaymillList<Client> list() {
    return this.list( null, null );
  }

  /**
   * This function returns a {@link List} of PayMill {@link Client} objects. In which order this list is returned depends on the
   * optional parameters. If <code>null</code> is given, no filter or order will be applied.
   * @param filter
   *          {@link Filter} or <code>null</code>
   * @param order
   *          {@link Order} or <code>null</code>
   * @return {@link PaymillList} which contains a {@link List} of PayMill {@link Client}s their total count and the mode test or live.
   */
  public PaymillList<Client> list( Client.Filter filter, Client.Order order ) {
    return RestfulUtils.list( ClientService.PATH, filter, order, Client.class, super.httpClient );
  }

  /**
   * Get the details of an existing PayMill {@link Client}.
   * @param client
   *          A {@link Client} with Id.
   * @return {@link Client} object, which represents a PayMill client.
   */
  public Client get( Client client ) {
    return RestfulUtils.show( ClientService.PATH, RestfulUtils.getIdByReflection( client ), Client.class, super.httpClient );
  }

  /**
   * Get the details of an existing PayMill {@link Client}.
   * @param clientId
   *          Id of the {@link Client}
   * @return {@link Client} object, which represents a PayMill client.
   */
  public Client get( String clientId ) {
    return RestfulUtils.show( ClientService.PATH, clientId, Client.class, super.httpClient );
  }

  public Client create() {
    return this.createWithEmailAndDescription( null, null );
  }

  public Client createWithEmail( String email ) {
    return this.createWithEmailAndDescription( email, null );
  }

  public Client createWithDescription( String description ) {
    return this.createWithEmailAndDescription( null, description );
  }

  /**
   * Creates a {@link Client} object.
   * @param email
   *          Mail address of the {@link Client} or <code>null</code>
   * @param description
   *          Description for the client or <code>null</code>
   * @return {@link Client} object, which represents a PayMill client.
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
   * @return {@link Client} object, which represents a PayMill client.
   */
  public Client update( Client client ) {
    return RestfulUtils.update( ClientService.PATH, client, Client.class, super.httpClient );
  }

  /**
   * This function deletes a client, but its transactions are not deleted.
   * @param client
   *          A {@link Client} with Id.
   * @return {@link Client} object without id, which represents a deleted PayMill client.
   */
  public Client delete( Client client ) {
    client = RestfulUtils.delete( ClientService.PATH, RestfulUtils.getIdByReflection( client ), Client.class, super.httpClient );
    client.setId( null );
    return client;
  }

  /**
   * This function deletes a client, but its transactions are not deleted.
   * @param clientId
   *          Id of the {@link Client}
   * @return {@link Client} object without id, which represents a deleted PayMill client.
   */
  public Client delete( String clientId ) {
    return this.delete( new Client( clientId ) );
  }

}

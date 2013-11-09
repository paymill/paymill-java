package com.paymill.services;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Client;
import com.paymill.utils.RestfulUtils;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ClientService implements PaymillService {

  private final static String PATH = "/clients";

  public List<Client> list() {
    return this.list( null, null );
  }

  public List<Client> list( Client.Filter filter, Client.Order order ) {
    return RestfulUtils.list( ClientService.PATH, filter, order, Client.class );
  }

  public Client get( Client client ) {
    return RestfulUtils.show( ClientService.PATH, client, Client.class );
  }

  public Client create( String email, String description ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    if( StringUtils.isNotBlank( email ) )
      params.add( "email", email );
    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfulUtils.create( ClientService.PATH, params, Client.class );
  }

  public Client update( Client client ) {
    return RestfulUtils.update( ClientService.PATH, client, Client.class );
  }

  public Client delete( Client client ) {
    return RestfulUtils.delete( ClientService.PATH, client, Client.class );
  }

}

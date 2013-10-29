package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Client;
import com.paymill.utils.RestfullUtils;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ClientService implements PaymillService {

  private final static String PATH = "/clients";

  public Client show( Client client ) {
    return RestfullUtils.show( ClientService.PATH, client, Client.class );
  }

  public Client create( String email, String description ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    if( StringUtils.isNotBlank( email ) )
      params.add( "email", email );
    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return RestfullUtils.create( ClientService.PATH, params, Client.class );
  }

  public Client update( Client client ) {
    return RestfullUtils.update( ClientService.PATH, client, Client.class );
  }

  public Client delete( Client client ) {
    return RestfullUtils.delete( ClientService.PATH, client, Client.class );
  }

}

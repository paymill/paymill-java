package com.paymill.services;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.Paymill;
import com.paymill.models.Client;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ClientService extends BaseService {

  private static String RESOURCE = "/clients";

  public Client create( String email, String description ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    if( StringUtils.isNotBlank( email ) )
      params.add( "email", email );
    if( StringUtils.isNotBlank( description ) )
      params.add( "description", description );

    return super.post( params );
  }

  public Client delete( Client client ) {
    return Paymill.delete( ClientService.RESOURCE, client );
  }

  @Override
  protected String getPath() {
    return ClientService.RESOURCE;
  }

  @Override
  protected Class<?> getClazz() {
    return Client.class;
  }

}

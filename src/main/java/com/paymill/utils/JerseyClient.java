package com.paymill.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.MultivaluedMap;

public final class JerseyClient implements HttpClient {
  private final Client httpClient;

  public JerseyClient( String apiKey ) {
    this( apiKey, null );
  }

  public JerseyClient( String apiKey, Integer timeout ) {
    this.httpClient = new Client();
    this.httpClient.setReadTimeout( timeout );
    this.httpClient.setConnectTimeout( timeout );
    this.httpClient.addFilter( new HTTPBasicAuthFilter( apiKey, StringUtils.EMPTY ) );
  }

  @Override
  public String get( String path ) {
    WebResource webResource = httpClient.resource( path );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  @Override
  public String get( String path, ParameterMap<String, String> params ) {
    WebResource webResource = httpClient.resource( path ).queryParams( convertMap( params ) );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  @Override
  public String post( String path, ParameterMap<String, String> params ) {
    WebResource webResource = httpClient.resource( path );
    ClientResponse response = webResource.post( ClientResponse.class, convertMap( params ) );
    return response.getEntity( String.class );
  }

  @Override
  public String put( String path, ParameterMap<String, String> params ) {
    WebResource webResource = httpClient.resource( path );
    ClientResponse response = webResource.put( ClientResponse.class, convertMap( params ) );
    return response.getEntity( String.class );
  }

  @Override
  public String delete( String path, ParameterMap<String, String> params ) {
    WebResource webResource = httpClient.resource( path );
    ClientResponse response = webResource.delete( ClientResponse.class, convertMap( params ) );
    return response.getEntity( String.class );
  }

  private static MultivaluedMap<String, String> convertMap(ParameterMap<String, String> map) {
    if( map == null ) {
      return null;
    }
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.putAll( map );
    return params;
  }
}

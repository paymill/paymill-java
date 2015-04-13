package com.paymill.samples.jerseyOneHttp;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.paymill.utils.HttpClient;
import com.paymill.utils.ParameterMap;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public final class JerseyOneClient implements HttpClient {
  private final Client httpClient;

  public JerseyOneClient( final String apiKey ) {
    this( apiKey, null );
  }

  public JerseyOneClient( final String apiKey, final Integer timeout ) {
    this.httpClient = new Client();
    if( timeout != null ) {
      this.httpClient.setReadTimeout( timeout );
      this.httpClient.setConnectTimeout( timeout );
    }
    this.httpClient.addFilter( new HTTPBasicAuthFilter( apiKey, StringUtils.EMPTY ) );
  }

  @Override
  public String get( final String path ) {
    WebResource webResource = this.httpClient.resource( path );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  @Override
  public String get( final String path, final ParameterMap<String, String> params ) {
    WebResource webResource = this.httpClient.resource( path ).queryParams( JerseyOneClient.convertMap( params ) );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  @Override
  public String post( final String path, final ParameterMap<String, String> params ) {
    WebResource webResource = this.httpClient.resource( path );
    ClientResponse response = webResource.post( ClientResponse.class, JerseyOneClient.convertMap( params ) );
    return response.getEntity( String.class );
  }

  @Override
  public String put( final String path, final ParameterMap<String, String> params ) {
    WebResource webResource = this.httpClient.resource( path );
    ClientResponse response = webResource.put( ClientResponse.class, JerseyOneClient.convertMap( params ) );
    return response.getEntity( String.class );
  }

  @Override
  public String delete( final String path, final ParameterMap<String, String> params ) {
    WebResource webResource = this.httpClient.resource( path );
    ClientResponse response = webResource.delete( ClientResponse.class, JerseyOneClient.convertMap( params ) );
    return response.getEntity( String.class );
  }

  private static MultivaluedMap<String, String> convertMap( final ParameterMap<String, String> map ) {
    if( map == null ) {
      return null;
    }
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    params.putAll( map );
    return params;
  }
}

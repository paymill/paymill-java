package com.paymill.utils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.paymill.utils.HttpClient;
import com.paymill.utils.ParameterMap;

public final class JerseyClient implements HttpClient {

  private final Client httpClient;

  public JerseyClient( final String apiKey ) {
    this( apiKey, null );
  }

  public JerseyClient( final String apiKey, final Integer timeout ) {
    ClientConfig configuration = new ClientConfig();
    if( timeout != null ) {
      configuration.property( ClientProperties.CONNECT_TIMEOUT, timeout );
      configuration.property( ClientProperties.READ_TIMEOUT, timeout );
    }
    this.httpClient = ClientBuilder.newClient( configuration );

    HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic( apiKey, StringUtils.EMPTY );
    this.httpClient.register( authFeature );
  }

  public String get( String path ) {
    WebTarget webResource = httpClient.target( path );
    Response response = webResource.request( MediaType.APPLICATION_JSON_TYPE ).get();
    return response.readEntity( String.class );
  }

  public String get( String path, ParameterMap<String, String> params ) {
    WebTarget webResource = httpClient.target( path );
    if( params != null ) {
      for( String key : params.keySet() ) {
        webResource = webResource.queryParam( key, params.get( key ).toArray() );
      }
    }
    Response response = webResource.request( MediaType.APPLICATION_JSON_TYPE ).get();
    return response.readEntity( String.class );
  }

  public String post( String path, ParameterMap<String, String> params ) {
    WebTarget webResource = httpClient.target( path );
    Response response = webResource.request( MediaType.APPLICATION_JSON_TYPE ).post( Entity.form( convertMap( params ) ) );
    return response.readEntity( String.class );
  }

  public String put( String path, ParameterMap<String, String> params ) {
    WebTarget webResource = httpClient.target( path );
    Response response = webResource.request( MediaType.APPLICATION_JSON_TYPE ).put( Entity.form( convertMap( params ) ) );
    return response.readEntity( String.class );
  }

  public String delete( String path, ParameterMap<String, String> params ) {
    WebTarget webResource = httpClient.target( path );
    if( params != null ) {
      for( String key : params.keySet() ) {
        webResource = webResource.queryParam( key, params.get( key ).toArray() );
      }
    }
    Response response = webResource.request( MediaType.APPLICATION_JSON_TYPE ).delete();
    return response.readEntity( String.class );
  }

  private static MultivaluedMap<String, String> convertMap( final ParameterMap<String, String> map ) {
    if( map == null ) {
      return null;
    }
    MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
    params.putAll( map );
    return params;
  }
}

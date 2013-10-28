package com.paymill;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import lombok.Getter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymill.services.BaseService;
import com.paymill.services.ClientService;
import com.paymill.services.PaymentService;
import com.paymill.services.TransactionService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public final class Paymill {

  public final static String                                    ENDPOINT = "https://api.paymill.com/v2";

  private static Map<Class<? extends BaseService>, BaseService> services;

  @Getter
  private static Client                                         httpClient;

  @Getter
  private static ObjectMapper                                   jacksonParser;

  static {
    Paymill.httpClient = new Client();
    Paymill.jacksonParser = new ObjectMapper();

    Paymill.services = new HashMap<Class<? extends BaseService>, BaseService>();

    Paymill.services.put( ClientService.class, new ClientService() );
    Paymill.services.put( TransactionService.class, new TransactionService() );
    Paymill.services.put( PaymentService.class, new PaymentService() );
  }

  public static void setApiKey( String apiKey ) {
    Paymill.httpClient.addFilter( new HTTPBasicAuthFilter( apiKey, "" ) );
  }

  public static void refreshApiKey( String apiKey ) {
    Paymill.httpClient.addFilter( new HTTPBasicAuthFilter( apiKey, "" ) );
  }

  @SuppressWarnings( "unchecked" )
  public static <T> T getService( Class<? extends BaseService> clazz ) {
    return (T) Paymill.services.get( clazz );
  }

  public static String post( String path, MultivaluedMap<String, String> params ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.post( ClientResponse.class, params );
    return response.getEntity( String.class );
  }

  public static String get( String path ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  @SuppressWarnings( "unchecked" )
  public static <T> T delete( String path, Object instance ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path + "/" + Paymill.getIdByReflection( instance ) );
    ClientResponse response = webResource.delete( ClientResponse.class );

    try {
      JsonNode wrappedNode = Paymill.getJacksonParser().readValue( response.getEntity( String.class ), JsonNode.class );
      if( wrappedNode.has( "data" ) )
        return (T) Paymill.getJacksonParser().readValue( wrappedNode.get( "data" ).toString(), instance.getClass() );
      else {
        throw new PaymillException( wrappedNode.get( "error" ).toString() );
      }
    } catch( IOException exc ) {
      exc.printStackTrace();
    }

    return null;
  }

  public static String getIdByReflection( Object instance ) {
    String id = "";
    try {
      Field field = instance.getClass().getDeclaredField( "id" );
      field.setAccessible( true );
      id = String.valueOf( field.get( instance ) );
    } catch( Exception exc ) {
      exc.printStackTrace();
    }
    return id;
  }

}

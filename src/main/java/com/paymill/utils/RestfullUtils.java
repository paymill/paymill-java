package com.paymill.utils;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.ws.rs.core.MultivaluedMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymill.Paymill;
import com.paymill.PaymillException;
import com.paymill.models.Updateable;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public final class RestfullUtils {

  public final static <T> T show( String path, Object instance, Class<?> clazz ) {
    String id = RestfullUtils.getIdByReflection( instance );
    ValidationUtils.validatesId( id );
    return RestfullUtils.deserializeObject( RestfullUtils.get( path + "/" + id ), clazz );
  }

  public final static <T> T create( String path, MultivaluedMap<String, String> params, Class<T> clazz ) {
    return RestfullUtils.deserializeObject( RestfullUtils.post( path, params ), clazz );
  }

  public final static <T> T update( String path, Object instance, Class<?> clazz ) {
    MultivaluedMap<String, String> params = RestfullUtils.prepareEditableParameters( instance );
    String id = RestfullUtils.getIdByReflection( instance );
    ValidationUtils.validatesId( id );
    return RestfullUtils.deserializeObject( RestfullUtils.put( path + "/" + id, params ), clazz );
  }

  public static <T> T delete( String path, Object instance, Class<?> clazz ) {
    String id = RestfullUtils.getIdByReflection( instance );
    ValidationUtils.validatesId( id );
    return RestfullUtils.deserializeObject( RestfullUtils.delete( path + "/" + id ), clazz );
  }

  @SuppressWarnings( "unchecked" )
  private final static <T> T deserializeObject( String content, Class<?> clazz ) {
    try {
      JsonNode wrappedNode = Paymill.getJacksonParser().readValue( content, JsonNode.class );
      if( wrappedNode.has( "data" ) )
        return (T) Paymill.getJacksonParser().readValue( wrappedNode.get( "data" ).toString(), clazz );
      if( wrappedNode.has( "error" ) )
        throw new PaymillException( wrappedNode.get( "error" ).toString() );
    } catch( IOException exc ) {
      throw new RuntimeException( exc );
    }
    return null;
  }

  private static MultivaluedMap<String, String> prepareEditableParameters( Object instance ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    for( Field field : instance.getClass().getDeclaredFields() ) {
      Updateable updateable = field.getAnnotation( Updateable.class );
      if( updateable != null ) {
        try {
          field.setAccessible( true );
          params.add( field.getName(), String.valueOf( field.get( instance ) ) );
        } catch( Exception exc ) {
          throw new RuntimeException( exc );
        }
      }
    }
    return params;
  }

  private static String getIdByReflection( Object instance ) {
    String id = "";
    try {
      Field field = instance.getClass().getDeclaredField( "id" );
      field.setAccessible( true );
      id = String.valueOf( field.get( instance ) );
    } catch( Exception exc ) {
      throw new RuntimeException( exc );
    }
    return id;
  }

  public final static String get( String path ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  private final static String post( String path, MultivaluedMap<String, String> params ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.post( ClientResponse.class, params );
    return response.getEntity( String.class );
  }

  private final static String put( String path, MultivaluedMap<String, String> params ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.put( ClientResponse.class, params );
    return response.getEntity( String.class );
  }

  private final static String delete( String path ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.delete( ClientResponse.class );
    return response.getEntity( String.class );
  }

}

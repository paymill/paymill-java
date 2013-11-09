package com.paymill.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymill.Paymill;
import com.paymill.PaymillException;
import com.paymill.models.SnakeCase;
import com.paymill.models.Updateable;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public final class RestfulUtils {

  public static <T> T list( String path, Object filter, Object order, Class<?> clazz ) {
    MultivaluedMap<String, String> params = RestfulUtils.prepareFilterParameters( filter );
    String param = RestfulUtils.prepareOrderParameter( order );
    if( StringUtils.isNotBlank( param ) && !StringUtils.startsWith( param, "_" ) ) {
      params.add( "order", param );
    }
    return RestfulUtils.deserializeList( RestfulUtils.get( path, params ), clazz );
  }

  public static <T> T show( String path, Object instance, Class<?> clazz ) {
    String id = RestfulUtils.getIdByReflection( instance );
    ValidationUtils.validatesId( id );
    return RestfulUtils.deserializeObject( RestfulUtils.get( path + "/" + id ), clazz );
  }

  public static <T> T create( String path, MultivaluedMap<String, String> params, Class<T> clazz ) {
    return RestfulUtils.deserializeObject( RestfulUtils.post( path, params ), clazz );
  }

  public static <T> T update( String path, Object instance, Class<?> clazz ) {
    MultivaluedMap<String, String> params = RestfulUtils.prepareEditableParameters( instance );
    String id = RestfulUtils.getIdByReflection( instance );
    ValidationUtils.validatesId( id );
    return RestfulUtils.deserializeObject( RestfulUtils.put( path + "/" + id, params ), clazz );
  }

  public static <T> T delete( String path, Object instance, Class<?> clazz ) {
    String id = RestfulUtils.getIdByReflection( instance );
    ValidationUtils.validatesId( id );
    return RestfulUtils.deserializeObject( RestfulUtils.delete( path + "/" + id ), clazz );
  }

  @SuppressWarnings( "unchecked" )
  private static <T> T deserializeObject( String content, Class<?> clazz ) {
    try {
      JsonNode wrappedNode = Paymill.getJacksonParser().readValue( content, JsonNode.class );
      if( wrappedNode.has( "data" ) ) {
        JsonNode dataNode = wrappedNode.get( "data" );
        if( !dataNode.isArray() ) {
          return (T) Paymill.getJacksonParser().readValue( dataNode.toString(), clazz );
        }
      }
      if( wrappedNode.has( "error" ) ) {
        throw new PaymillException( wrappedNode.get( "error" ).toString() );
      }
    } catch( IOException exc ) {
      throw new RuntimeException( exc );
    }
    return null;
  }

  @SuppressWarnings( "unchecked" )
  private static <T> T deserializeList( String content, Class<?> clazz ) {
    try {
      JsonNode wrappedNode = Paymill.getJacksonParser().readValue( content, JsonNode.class );
      if( wrappedNode.has( "data" ) ) {
        JsonNode dataNode = wrappedNode.get( "data" );
        if( dataNode.isArray() ) {
          List<Object> objects = new ArrayList<Object>();
          for( Object object : Paymill.getJacksonParser().readValue( dataNode.toString(), List.class ) ) {
            try {
              //              ((Map) object).get( "" )
              objects.add( Paymill.getJacksonParser().readValue( Paymill.getJacksonParser().writeValueAsString( object ), clazz ) );
            } catch( Exception exc ) {
              System.out.println( object );
            }
          }
          return (T) objects;
        }
      }
      if( wrappedNode.has( "error" ) ) {
        throw new PaymillException( wrappedNode.get( "error" ).toString() );
      }
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

  public static String getIdByReflection( Object instance ) {
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

  public static String get( String path, MultivaluedMap<String, String> params ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path ).queryParams( params );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  public static String get( String path ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  private static String post( String path, MultivaluedMap<String, String> params ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.post( ClientResponse.class, params );
    return response.getEntity( String.class );
  }

  private static String put( String path, MultivaluedMap<String, String> params ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.put( ClientResponse.class, params );
    return response.getEntity( String.class );
  }

  private static String delete( String path ) {
    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + path );
    ClientResponse response = webResource.delete( ClientResponse.class );
    return response.getEntity( String.class );
  }

  private static MultivaluedMap<String, String> prepareFilterParameters( Object instance ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    if( instance == null )
      return params;
    try {
      for( Field field : instance.getClass().getDeclaredFields() ) {
        field.setAccessible( true );
        Object value = field.get( instance );
        if( value != null ) {
          params.add( field.getAnnotation( SnakeCase.class ).value(), String.valueOf( value ) );
        }
      }
    } catch( Exception exc ) {

    }
    return params;
  }

  private static String prepareOrderParameter( Object instance ) {
    String order = StringUtils.EMPTY;
    String sortEntry = StringUtils.EMPTY;
    try {
      for( Field field : instance.getClass().getDeclaredFields() ) {
        field.setAccessible( true );
        if( field.getBoolean( instance ) ) {
          SnakeCase annotation = field.getAnnotation( SnakeCase.class );
          if( annotation.order() ) {
            order += "_" + annotation.value();
          } else {
            sortEntry = annotation.value();
          }
        }
      }
    } catch( Exception exc ) {

    }
    return sortEntry + order;
  }

}

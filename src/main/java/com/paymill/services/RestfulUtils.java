package com.paymill.services;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymill.context.PaymillContext;
import com.paymill.exceptions.PaymillException;
import com.paymill.models.PaymillList;
import com.paymill.models.SnakeCase;
import com.paymill.models.Updateable;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

final class RestfulUtils {

  private final static String ENDPOINT = "https://api.paymill.com/v2.1";

  static <T> PaymillList<T> list( String path, Object filter, Object order, Integer count, Integer offset, Class<?> clazz, Client httpClient ) {
    MultivaluedMap<String, String> params = RestfulUtils.prepareFilterParameters( filter );
    String param = RestfulUtils.prepareOrderParameter( order );
    if( StringUtils.isNotBlank( param ) && !StringUtils.startsWith( param, "_" ) ) {
      params.add( "order", param );
    }
    if( count != null && count > 0 ) {
      params.add( "count", String.valueOf( count ) );
    }
    if( offset != null && offset >= 0 ) {
      params.add( "offset", String.valueOf( offset ) );
    }
    return RestfulUtils.deserializeList( RestfulUtils.get( path, params, httpClient ), clazz );
  }

  static <T> T show( String path, T target, Class<?> clazz, Client httpClient ) {
    String id = RestfulUtils.getIdByReflection( target );
    T source = RestfulUtils.deserializeObject( RestfulUtils.get( path + "/" + id, httpClient ), clazz );
    return RestfulUtils.refreshInstance( source, target );
  }

  static <T> T create( String path, MultivaluedMap<String, String> params, Class<T> clazz, Client httpClient ) {
    return RestfulUtils.deserializeObject( RestfulUtils.post( path, params, httpClient ), clazz );
  }

  static <T> T update( String path, T target, Class<?> clazz, Client httpClient ) {
    MultivaluedMap<String, String> params = RestfulUtils.prepareEditableParameters( target );
    String id = RestfulUtils.getIdByReflection( target );
    T source = RestfulUtils.deserializeObject( RestfulUtils.put( path + "/" + id, params, httpClient ), clazz );
    return RestfulUtils.refreshInstance( source, target );
  }

  static <T> T update( String path, T target, MultivaluedMap<String, String> params, Class<?> clazz, Client httpClient ) {
    String id = RestfulUtils.getIdByReflection( target );
    T source = RestfulUtils.deserializeObject( RestfulUtils.put( path + "/" + id, params, httpClient ), clazz );
    return RestfulUtils.refreshInstance( source, target );
  }

  static <T> T delete( String path, T target, MultivaluedMap<String, String> params, Class<?> clazz, Client httpClient ) {
    String id = RestfulUtils.getIdByReflection( target );
    T source = RestfulUtils.deserializeObject( RestfulUtils.delete( path + "/" + id, params, httpClient ), clazz );
    return RestfulUtils.refreshInstance( source, target );
  }

  static <T> T delete( String path, T target, Class<?> clazz, Client httpClient ) {
    String id = RestfulUtils.getIdByReflection( target );
    T source = RestfulUtils.deserializeObject( RestfulUtils.delete( path + "/" + id, null, httpClient ), clazz );
    return RestfulUtils.refreshInstance( source, target );
  }

  private static String getIdByReflection( Object instance ) {
    if( instance == null )
      throw new RuntimeException( "Can not obtain Id from null" );
    try {
      Field field = instance.getClass().getDeclaredField( "id" );
      field.setAccessible( true );
      String id = String.valueOf( field.get( instance ) );
      ValidationUtils.validatesId( id );
      return id;
    } catch( Exception exc ) {
      throw new RuntimeException( exc );
    }
  }

  @SuppressWarnings( "unchecked" )
  private static <T> T deserializeObject( String content, Class<?> clazz ) {
    try {
      JsonNode wrappedNode = PaymillContext.PARSER.readValue( content, JsonNode.class );
      if( wrappedNode.has( "data" ) ) {
        JsonNode dataNode = wrappedNode.get( "data" );
        if( !dataNode.isArray() ) {
          return (T) PaymillContext.PARSER.readValue( dataNode.toString(), clazz );
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
  private static <T> PaymillList<T> deserializeList( String content, Class<?> clazz ) {
    try {
      JsonNode wrappedNode = PaymillContext.PARSER.readValue( content, JsonNode.class );
      PaymillList<T> wrapper = PaymillContext.PARSER.readValue( wrappedNode.toString(), PaymillList.class );
      if( wrappedNode.has( "data" ) ) {
        JsonNode dataNode = wrappedNode.get( "data" );
        if( dataNode.isArray() ) {
          List<T> objects = new ArrayList<T>();
          for( Object object : PaymillContext.PARSER.readValue( wrappedNode.toString(), PaymillList.class ).getData() ) {
            try {
              objects.add( (T) PaymillContext.PARSER.readValue( PaymillContext.PARSER.writeValueAsString( object ), clazz ) );
            } catch( Exception exc ) {
              throw new RuntimeException( exc );
            }
          }
          wrapper.setData( objects );
          return wrapper;
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
          Object value = field.get( instance );
          if( value != null ) {
            Class<?> clazz = value.getClass();
            if( ClassUtils.isPrimitiveOrWrapper( clazz ) || ClassUtils.getSimpleName( clazz ).equals( "String" ) ) {
              params.add( updateable.value(), String.valueOf( value ) );
            } else {
              // not primitive type, assume ID
              String id = null;
              try {
                id = RestfulUtils.getIdByReflection( value );
              } catch( Exception e ) {
                //that's normal, object does not have an id
              }
              if( id != null ) {
                params.add( updateable.value(), id );
              } else {
                params.add( updateable.value(), value.toString() );
              }
            }
          }
        } catch( Exception exc ) {
          throw new RuntimeException( exc );
        }
      }
    }
    return params;
  }

  private static String get( String path, Client httpClient ) {
    WebResource webResource = httpClient.resource( RestfulUtils.ENDPOINT + path );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  private static String get( String path, MultivaluedMap<String, String> params, Client httpClient ) {
    WebResource webResource = httpClient.resource( RestfulUtils.ENDPOINT + path ).queryParams( params );
    ClientResponse response = webResource.get( ClientResponse.class );
    return response.getEntity( String.class );
  }

  private static String post( String path, MultivaluedMap<String, String> params, Client httpClient ) {
    WebResource webResource = httpClient.resource( RestfulUtils.ENDPOINT + path );
    ClientResponse response = webResource.post( ClientResponse.class, params );
    return response.getEntity( String.class );
  }

  private static String put( String path, MultivaluedMap<String, String> params, Client httpClient ) {
    WebResource webResource = httpClient.resource( RestfulUtils.ENDPOINT + path );
    ClientResponse response = webResource.put( ClientResponse.class, params );
    return response.getEntity( String.class );
  }

  private static String delete( String path, MultivaluedMap<String, String> params, Client httpClient ) {
    WebResource webResource = httpClient.resource( RestfulUtils.ENDPOINT + path );
    ClientResponse response = webResource.delete( ClientResponse.class, params );
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
      throw new RuntimeException( exc );
    }
    return params;
  }

  private static String prepareOrderParameter( Object instance ) {
    if( instance == null )
      return StringUtils.EMPTY;
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
      throw new RuntimeException( exc );
    }
    return sortEntry + order;
  }

  private static <T> T refreshInstance( T source, T target ) {
    if( source == null ) {
      return target;
    }
    try {
      BeanUtils.copyProperties( target, source );
    } catch( Exception exc ) {
      throw new RuntimeException( exc );
    }
    return target;
  }

}

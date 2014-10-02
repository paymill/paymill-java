package com.paymill.services;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.paymill.utils.HttpClient;
import com.paymill.utils.ParameterMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymill.context.PaymillContext;
import com.paymill.exceptions.PaymillException;
import com.paymill.models.PaymillList;
import com.paymill.models.SnakeCase;
import com.paymill.models.Updateable;

final class RestfulUtils {

  private final static String ENDPOINT = "https://api.paymill.com/v2.1";

  static <T> PaymillList<T> list( String path, Object filter, Object order, Integer count, Integer offset, Class<?> clazz, HttpClient httpClient ) {
    ParameterMap<String, String> params = RestfulUtils.prepareFilterParameters( filter );
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
    return RestfulUtils.deserializeList( httpClient.get( ENDPOINT + path, params ), clazz );
  }

  static <T> T show( String path, T target, Class<?> clazz, HttpClient httpClient ) {
    String id = RestfulUtils.getIdByReflection( target );
    T source = RestfulUtils.deserializeObject( httpClient.get( ENDPOINT + path + "/" + id ), clazz );
    return RestfulUtils.refreshInstance( source, target );
  }

  static <T> T create( String path, ParameterMap<String, String> params, Class<T> clazz, HttpClient httpClient ) {
    return RestfulUtils.deserializeObject( httpClient.post( ENDPOINT + path, params ), clazz );
  }

  static <T> T update( String path, T target, Class<?> clazz, HttpClient httpClient ) {
    ParameterMap<String, String> params = RestfulUtils.prepareEditableParameters( target );
    String id = RestfulUtils.getIdByReflection( target );
    T source = RestfulUtils.deserializeObject( httpClient.put( ENDPOINT + path + "/" + id, params ), clazz );
    return RestfulUtils.refreshInstance( source, target );
  }

  static <T> T update( String path, T target, ParameterMap<String, String> params, boolean includeTargetUpdateables, Class<?> clazz, HttpClient httpClient ) {
    String id = RestfulUtils.getIdByReflection( target );
    if( includeTargetUpdateables ) {
      params.putAll( RestfulUtils.prepareEditableParameters( target ) );
    }
    T source = RestfulUtils.deserializeObject( httpClient.put( ENDPOINT + path + "/" + id, params ), clazz );
    return RestfulUtils.refreshInstance( source, target );
  }

  static <T> T delete( String path, T target, ParameterMap<String, String> params, Class<?> clazz, HttpClient httpClient ) {
    String id = RestfulUtils.getIdByReflection( target );
    T source = RestfulUtils.deserializeObject( httpClient.delete( ENDPOINT + path + "/" + id, params ), clazz );
    return RestfulUtils.refreshInstance( source, target );
  }

  static <T> T delete( String path, T target, Class<?> clazz, HttpClient httpClient ) {
    String id = RestfulUtils.getIdByReflection( target );
    T source = RestfulUtils.deserializeObject( httpClient.delete( ENDPOINT + path + "/" + id, null ), clazz );
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

  private static ParameterMap<String, String> prepareEditableParameters( Object instance ) {
    ParameterMap<String, String> params = new ParameterMap<String, String>();

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

  private static ParameterMap<String, String> prepareFilterParameters( Object instance ) {
    ParameterMap<String, String> params = new ParameterMap<String, String>();

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

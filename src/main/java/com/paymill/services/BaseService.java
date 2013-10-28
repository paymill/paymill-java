package com.paymill.services;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.ws.rs.core.MultivaluedMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymill.Paymill;
import com.paymill.PaymillException;
import com.paymill.models.Updateable;
import com.paymill.utils.ValidationUtils;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public abstract class BaseService {

  public <T> T show( String id ) {
    return this.get( id );
  }

  @SuppressWarnings( "unchecked" )
  public <T> T update( Object instance ) {
    MultivaluedMap<String, String> params = new MultivaluedMapImpl();

    for( Field field : instance.getClass().getDeclaredFields() ) {
      Updateable updateable = field.getAnnotation( Updateable.class );
      if( updateable != null ) {
        try {
          field.setAccessible( true );
          params.add( field.getName(), String.valueOf( field.get( instance ) ) );
        } catch( Exception exc ) {
          exc.printStackTrace();
        }
      }
    }

    WebResource webResource = Paymill.getHttpClient().resource( Paymill.ENDPOINT + this.getPath() + "/" + Paymill.getIdByReflection( instance ) );
    ClientResponse response = webResource.put( ClientResponse.class, params );

    try {
      JsonNode wrappedNode = Paymill.getJacksonParser().readValue( response.getEntity( String.class ), JsonNode.class );
      if( wrappedNode.has( "data" ) )
        return (T) Paymill.getJacksonParser().readValue( wrappedNode.get( "data" ).toString(), this.getClazz() );
      else {
        throw new PaymillException( wrappedNode.get( "error" ).toString() );
      }
    } catch( IOException exc ) {
      exc.printStackTrace();
    }

    return null;
  }

  @SuppressWarnings( "unchecked" )
  protected <T> T post( MultivaluedMap<String, String> params ) {
    try {
      JsonNode wrappedNode = Paymill.getJacksonParser().readValue( Paymill.post( this.getPath(), params ), JsonNode.class );
      if( wrappedNode.has( "data" ) )
        return (T) Paymill.getJacksonParser().readValue( wrappedNode.get( "data" ).toString(), this.getClazz() );
      else {
        throw new PaymillException( wrappedNode.get( "error" ).toString() );
      }
    } catch( IOException exc ) {
      exc.printStackTrace();
    }
    return null;
  }

  @SuppressWarnings( "unchecked" )
  protected final <T> T get( String id ) {
    ValidationUtils.validatesId( id );
    try {
      JsonNode wrappedNode = Paymill.getJacksonParser().readValue( Paymill.get( this.getPath() + "/" + id ), JsonNode.class );
      if( wrappedNode.has( "data" ) )
        return (T) Paymill.getJacksonParser().readValue( wrappedNode.get( "data" ).toString(), this.getClazz() );
      else {
        throw new PaymillException( wrappedNode.get( "error" ).toString() );
      }
    } catch( IOException exc ) {
      exc.printStackTrace();
    }
    return null;
  }

  protected abstract String getPath();

  protected abstract Class<?> getClazz();

}

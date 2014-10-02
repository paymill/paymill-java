package com.paymill.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class Deserializer<T> extends StdDeserializer<T> implements ResolvableDeserializer {

  private static final long         serialVersionUID = -1737541287590838196L;
  private final JsonDeserializer<?> defaultDeserializer;

  public Deserializer( JsonDeserializer<?> defaultDeserializer ) {
    super( Client.class );
    this.defaultDeserializer = defaultDeserializer;
  }

  @Override
  public T deserialize( JsonParser parser, DeserializationContext context ) throws IOException, JsonProcessingException {
    if( parser.getCurrentToken() == JsonToken.START_ARRAY ) {
      return null;
    }
    @SuppressWarnings( "unchecked" )
    T deserializedObject = (T) defaultDeserializer.deserialize( parser, context );
    return deserializedObject;
  }

  @Override
  public void resolve( DeserializationContext ctxt ) throws JsonMappingException {
    ((ResolvableDeserializer) defaultDeserializer).resolve( ctxt );
  }

  public static SimpleModule getDeserializerModule() {
    SimpleModule module = new SimpleModule();
    module.setDeserializerModifier( new BeanDeserializerModifier() {
      @Override
      public JsonDeserializer<?> modifyDeserializer( DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer ) {
        if( beanDesc.getBeanClass() == Client.class ) {
          return new Deserializer<Client>( deserializer );
        } else if( beanDesc.getBeanClass() == Fee.class ) {
          return new Deserializer<Fee>( deserializer );
        } else if( beanDesc.getBeanClass() == Interval.class ) {
          return new Deserializer<Interval>( deserializer );
        } else if( beanDesc.getBeanClass() == Offer.class ) {
          return new Deserializer<Offer>( deserializer );
        } else if( beanDesc.getBeanClass() == Payment.class ) {
          return new Deserializer<Payment>( deserializer );
        } else if( beanDesc.getBeanClass() == Preauthorization.class ) {
          return new Deserializer<Preauthorization>( deserializer );
        } else if( beanDesc.getBeanClass() == Refund.class ) {
          return new Deserializer<Refund>( deserializer );
        } else if( beanDesc.getBeanClass() == Subscription.class ) {
          return new Deserializer<Subscription>( deserializer );
        } else if( beanDesc.getBeanClass() == Transaction.class ) {
          return new Deserializer<Transaction>( deserializer );
        } else if( beanDesc.getBeanClass() == Webhook.class ) {
          return new Deserializer<Webhook>( deserializer );
        } else {
          return deserializer;
        }
      }
    } );
    return module;
  }
}

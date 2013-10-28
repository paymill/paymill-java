package com.paymill.models;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@Data
public class Fee {

  private Fee.Type type;
  private String   application;
  private String   payment;
  private Integer  amount;

  public enum Type {

    APPLICATION("application");

    private String value;

    private Type( String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Type create( String value ) {
      for( Type type : Type.values() ) {
        if( type.getValue().equals( value ) ) {
          return type;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Fee.Type" );
    }
  }

}

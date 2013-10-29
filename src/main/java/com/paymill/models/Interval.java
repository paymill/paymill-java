package com.paymill.models;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Interval {

  private Integer       interval;
  private Interval.Unit unit;

  public Interval( String interval ) {
    String[] parts = StringUtils.split( interval );
    this.interval = Integer.parseInt( parts[0] );
    this.unit = Interval.Unit.create( parts[1] );
  }

  @Override
  public String toString() {
    return interval + " " + unit;
  }

  public enum Unit {
    DAY("DAY"), WEEK("WEEK"), MONTH("MONTH"), YEAR("YEAR");

    private String value;

    private Unit( String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Unit create( String value ) {
      for( Unit unit : Unit.values() ) {
        if( unit.getValue().equals( value ) ) {
          return unit;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Interval.Unit" );
    }
  }

}

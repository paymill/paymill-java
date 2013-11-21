package com.paymill.models;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties( ignoreUnknown = true )
public class Interval {

  private Integer       interval;
  private Interval.Unit unit;

  public Interval( final String interval ) {
    String[] parts = StringUtils.split( interval );
    this.interval = Integer.parseInt( parts[0] );
    this.unit = Interval.Unit.create( parts[1] );
  }

  public Integer getInterval() {
    return this.interval;
  }

  public void setInterval( final Integer interval ) {
    this.interval = interval;
  }

  public Interval.Unit getUnit() {
    return this.unit;
  }

  public void setUnit( final Interval.Unit unit ) {
    this.unit = unit;
  }

  public enum Unit {
    DAY("DAY"), WEEK("WEEK"), MONTH("MONTH"), YEAR("YEAR");

    private String value;

    private Unit( final String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Unit create( final String value ) {
      for( Unit unit : Unit.values() ) {
        if( unit.getValue().equals( value ) ) {
          return unit;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Interval.Unit" );
    }
  }

  @Override
  public String toString() {
    return this.interval + " " + this.unit;
  }

}

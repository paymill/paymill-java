package com.paymill.models;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

public class Interval {

  public static Interval.Period period( Integer interval, Unit unit ) {
    return new Interval.Period( interval, unit );
  }

  public static Interval.PeriodWithChargeDay periodWithChargeDay( Integer interval, Unit unit, Weekday weekday ) {
    return new Interval.PeriodWithChargeDay( interval, unit, weekday );
  }

  public static Interval.PeriodWithChargeDay periodWithChargeDay( Integer interval, Unit unit ) {
    return new Interval.PeriodWithChargeDay( interval, unit, null );
  }

  @JsonIgnoreProperties( ignoreUnknown = true )
  public static class Period {

    protected Integer interval;
    protected Unit    unit;

    public Period( final String interval ) {
      try {
        String[] intervalParts = StringUtils.split( interval );
        this.interval = Integer.parseInt( intervalParts[0] );
        this.unit = Unit.create( intervalParts[1] );
      } catch( ArrayIndexOutOfBoundsException e ) {
        throw new IllegalArgumentException( "Invalid period:" + interval );
      }
    }

    private Period() {
    }

    private Period( Integer interval, Unit unit ) {
      this.interval = interval;
      this.unit = unit;
    }

    public Integer getInterval() {
      return interval;
    }

    public void setInterval( Integer interval ) {
      this.interval = interval;
    }

    public Unit getUnit() {
      return unit;
    }

    public void setUnit( Unit unit ) {
      this.unit = unit;
    }

    @Override
    public String toString() {
      return this.interval + " " + this.unit;
    }
  }

  @JsonIgnoreProperties( ignoreUnknown = true )
  public static class PeriodWithChargeDay extends Period {

    private Weekday weekday;

    public PeriodWithChargeDay( String interval ) {
      super();
      try {
        String[] weekdayParts = StringUtils.split( interval, ',' );
        if( weekdayParts.length > 1 ) {
          this.weekday = Weekday.create( weekdayParts[1] );
        }
        String[] intervalParts = StringUtils.split( interval );
        this.interval = Integer.parseInt( intervalParts[0] );
        this.unit = Unit.create( intervalParts[1] );
      } catch( ArrayIndexOutOfBoundsException e ) {
        throw new IllegalArgumentException( "Invalid period:" + interval );
      }
    }

    private PeriodWithChargeDay( Integer interval, Unit unit, Weekday weekday ) {
      this.interval = interval;
      this.unit = unit;
      this.weekday = weekday;
    }

    /**
     * The weekday
     * @return the weekday or <code>null</code>, if none was defined
     */
    public Weekday getWeekday() {
      return weekday;
    }

    public void setWeekday( Weekday weekday ) {
      this.weekday = weekday;
    }

    @Override
    public String toString() {
      return (getWeekday() == null) ? super.toString() : super.toString() + "," + getWeekday();
    }
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

    @Override
    public String toString() {
      return this.value;
    }
  }

  public enum Weekday {
    MONDAY("MONDAY"), TUESDAY("TUESDAY"), WEDNESDAY("WEDNESDAY"), THURSDAY("THURSDAY"), FRIDAY("FRIDAY"), SATURDAY("SATURDAY"), SUNDAY("SUNDAY");

    private String value;

    private Weekday( final String value ) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    @JsonCreator
    public static Weekday create( final String value ) {
      for( Weekday weekday : Weekday.values() ) {
        if( weekday.getValue().equals( value ) ) {
          return weekday;
        }
      }
      throw new IllegalArgumentException( "Invalid value for Interval.Unit" );
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

}

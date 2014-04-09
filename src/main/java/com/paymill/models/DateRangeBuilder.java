package com.paymill.models;

import java.util.Date;

final class DateRangeBuilder {

  private final static int OFFSET = 1000;

  final static String execute( final Date startDate, final Date endDate ) {
    if( startDate == null )
      throw new IllegalArgumentException( "Start date can not be null" );

    String range = String.valueOf( startDate.getTime() / DateRangeBuilder.OFFSET );
    if( endDate != null ) {
      range += "-" + String.valueOf( endDate.getTime() / DateRangeBuilder.OFFSET );
    }

    return range;
  }

}

package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Offer {
  //TODO[VNi]: handle subscription_count

  private String   id;

  @Updateable
  private String   name;

  private Integer  amount;

  private Interval interval;

  @JsonProperty( "trial_period_days" )
  private Integer  trialPeriodDays;

  private String   currency;

  @JsonProperty( "created_at" )
  private Date     createdAt;

  @JsonProperty( "updated_at" )
  private Date     updatedAt;

  @JsonProperty( "app_id" )
  private String   appId;

  public void setInterval( String interval ) {
    this.interval = new Interval( interval );
  }

}

package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Subscription {

  public Subscription() {
    super();
  }

  public Subscription( String id ) {
    this.id = id;
  }

  private String  id;

  private Offer   offer;

  private Boolean livemode;

  @JsonProperty( "cancel_at_period_end" )
  private Boolean cancelAtPeriodEnd;

  @JsonProperty( "trial_start" )
  private Date    trialStart;

  @JsonProperty( "trial_end" )
  private Date    trialEnd;

  @JsonProperty( "next_capture_at" )
  private Date    nextCaptureAt;

  @JsonProperty( "created_at" )
  private Date    createdAt;

  @JsonProperty( "updated_at" )
  private Date    updatedAt;

  @JsonProperty( "canceled_at" )
  private Date    canceledAt;

  private Payment payment;

  private Client  client;

  @JsonProperty( "app_id" )
  private String  appId;

}

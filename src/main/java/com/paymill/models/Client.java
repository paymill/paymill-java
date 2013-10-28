package com.paymill.models;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class Client {

  private String             id;

  @Updateable
  private String             email;

  @Updateable
  private String             description;

  @JsonProperty( "created_at" )
  private Date               createdAt;

  @JsonProperty( "updated_at" )
  private Date               updatedAt;

  @JsonProperty( "payment" )
  private List<Payment>       payments;

  @JsonProperty( "subscription" )
  private List<Subscription> subscriptions;

  @JsonProperty( "app_id" )
  private String             appId;

}

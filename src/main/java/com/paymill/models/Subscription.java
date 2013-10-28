package com.paymill.models;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class Subscription {
  private String  id;
  private Offer   offer;
  private Client  client;
  private Payment payment;
  @JsonIgnore
  private Boolean cancelAtPeriodEnd;
  private Date    trialStart;
  private Date    trialEnd;
  private Date    canceledAt;
  private Date    createdAt;
  private Date    updatedAt;
  private Date    nextCaptureAt;

}

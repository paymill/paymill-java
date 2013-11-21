package com.paymill.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class PaymillList<T> {

  public PaymillList() {
    this.data = new ArrayList<T>();
  }

  private List<T> data;

  @JsonProperty( "data_count" )
  private int     dataCount;

  private String  mode;

}

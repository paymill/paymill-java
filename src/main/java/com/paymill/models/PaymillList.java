package com.paymill.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties( ignoreUnknown = true )
public final class PaymillList<T> {

  public PaymillList() {
    this.data = new ArrayList<T>();
  }

  private List<T> data;

  @JsonProperty( "data_count" )
  private int     dataCount;

  public List<T> getData() {
    return this.data;
  }

  public void setData( final List<T> data ) {
    this.data = data;
  }

  public int getDataCount() {
    return this.dataCount;
  }

  public void setDataCount( final int dataCount ) {
    this.dataCount = dataCount;
  }

}

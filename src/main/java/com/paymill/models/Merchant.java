package com.paymill.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties( ignoreUnknown = true )
public class Merchant {

  public Merchant() {
    super();
  }

  public Merchant( String identifier ) {
    this.identifier = identifier;
  }

  @JsonProperty( "identifier_key" )
  private String       identifier;
  private String       email;
  private String       locale;
  private String       country;
  private List<String> methods;

  /**
   * @return unique identifier of this merchant.
   */
  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier( String identifier ) {
    this.identifier = identifier;
  }

  /**
   * @return email address of this merchant.
   */
  public String getEmail() {
    return email;
  }

  public void setEmail( String email ) {
    this.email = email;
  }

  /**
   * @return culture setting of this merchant.
   */
  public String getLocale() {
    return locale;
  }

  public void setLocale( String locale ) {
    this.locale = locale;
  }

  /**
   * @return country code of this merchant.
   */
  public String getCountry() {
    return country;
  }

  public void setCountry( String country ) {
    this.country = country;
  }

  /**
   * @return List of activated card brands of this merchant.
   */
  public List<String> getMethods() {
    return methods;
  }

  public void setMethods( List<String> methods ) {
    this.methods = methods;
  }

}

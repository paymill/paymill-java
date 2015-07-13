package com.paymill.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An address object belongs to exactly one transaction and can represent either its shipping address or billing address. Note
 * that state and postal_code are mandatory for PayPal transactions in certain countries, please consult PayPal documentation for
 * more details.
 * 
 * @author Vassil Nikolov
 * @since 5.1.0
 */
public class Address {

  private String name;

  @JsonProperty("street_address")
  private String streetAddress;

  @JsonProperty("street_address_addition")
  private String streetAddressAddition;

  private String city;

  private String state;

  @JsonProperty("postal_code")
  private String postalCode;

  private String country;

  private String phone;

  /**
   * Name of recipient, max. 128 characters
   * 
   * @return
   */
  public String getName() {
    return this.name;
  }

  /**
   * Name of recipient, max. 128 characters
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Street address (incl. street number), max. 100 characters
   * 
   * @return {@link String}
   */
  public String getStreetAddress() {
    return this.streetAddress;
  }

  /**
   * Street address (incl. street number), max. 100 characters
   * 
   * @param streetAddress
   */
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  /**
   * Addition to street address (e.g. building, floor, or c/o), max. 100 characters
   * 
   * @return {@link String}
   */
  public String getStreetAddressAddition() {
    return this.streetAddressAddition;
  }

  /**
   * Addition to street address (e.g. building, floor, or c/o), max. 100 characters
   * 
   * @param streetAddressAddition
   */
  public void setStreetAddressAddition(String streetAddressAddition) {
    this.streetAddressAddition = streetAddressAddition;
  }

  /**
   * City, max. 40 characters
   * 
   * @return {@link String}
   */
  public String getCity() {
    return this.city;
  }

  /**
   * City, max. 40 characters
   * 
   * @param city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * State or province, max. 40 characters
   * 
   * @return {@link String}
   */
  public String getState() {
    return this.state;
  }

  /**
   * State or province, max. 40 characters
   * 
   * @param state
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Country-specific postal code, max. 20 characters
   * 
   * @return {@link String}
   */
  public String getPostalCode() {
    return this.postalCode;
  }

  /**
   * Country-specific postal code, max. 20 characters
   * 
   * @param postalCode
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * 2-letter country code according to ISO 3166-1 alpha-2
   * 
   * @return {@link String}
   */
  public String getCountry() {
    return this.country;
  }

  /**
   * 2-letter country code according to ISO 3166-1 alpha-2
   * 
   * @param country
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Contact phone number, max. 20 characters
   * 
   * @return {@link String}
   */
  public String getPhone() {
    return this.phone;
  }

  /**
   * Contact phone number, max. 20 characters
   * 
   * @param phone
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

}

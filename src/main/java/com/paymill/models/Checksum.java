package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Checksum validation is a simple method to ensure the integrity of transferred data. Basically, we generate a hash out of the
 * given parameters and your private API key. If you send us a request with transaction data and the generated checksum, we can
 * easily validate the data. To make the checksum computation as easy as possible we provide this endpoint for you.
 *
 * For transactions that are started client-side, e.g. PayPal checkout, it is required to first create a checksum on your server
 * and then provide that checksum when starting the transaction in the browser. The checksum needs to contain all data required to
 * subsequently create the actual transaction.
 * 
 * @author Vassil Nikolov
 * @since 5.1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Checksum {

  private String id;

  private String type;

  private String checksum;

  private String data;

  @JsonProperty("created_at")
  private Date createdAt;

  @JsonProperty("updated_at")
  private Date updatedAt;

  @JsonProperty("app_id")
  private String appId;

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getChecksum() {
    return this.checksum;
  }

  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  public String getData() {
    return this.data;
  }

  public void setData(String data) {
    this.data = data;
  }

  /**
   * Returns App (ID) that created this offer or <code>null</code> if created by yourself.
   * 
   * @return {@link String} or <code>null</code>.
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * Sets App (ID) that created this offer or <code>null</code> if created by yourself.
   * 
   * @param appId
   *          {@link String}
   */
  public void setAppId(String appId) {
    this.appId = appId;
  }

  /**
   * Returns the creation date.
   * 
   * @return {@link Date}
   */
  public Date getCreatedAt() {
    return this.createdAt;
  }

  /**
   * Set the creation date.
   * 
   * @param createdAt
   *          {@link Date}
   */
  @JsonIgnore
  public void setCreatedAt(final Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Set the creation date.
   * 
   * @param seconds
   *          Creation date representation is seconds.
   */
  public void setCreatedAt(final long seconds) {
    this.createdAt = new Date(seconds * 1000);
  }

  /**
   * Returns the last update.
   * 
   * @return {@link Date}
   */
  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  /**
   * Sets the last update.
   * 
   * @param updatedAt
   *          {@link Date}
   */
  @JsonIgnore
  public void setUpdatedAt(final Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Sets the last update.
   * 
   * @param seconds
   *          Last update representation is seconds.
   */
  public void setUpdatedAt(final long seconds) {
    this.updatedAt = new Date(seconds * 1000);
  }

}

// curl https://api.paymill.com/v2.1/checksums -u "$API_TEST_KEY:" -d "checksum_type=paypal" -d "amount=4200" -d "currency=EUR" -d
// "description=Test Transaction" -d "return_url=https://www.example.com/store/checkout/result" -d
// "cancel_url=https://www.example.com/store/checkout/retry"

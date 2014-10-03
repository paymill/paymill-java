package com.paymill.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties( ignoreUnknown = true )
public class Invoice {

  public Invoice() {
    super();
  }

  public Invoice( String invoiceNumber ) {
    this.invoiceNumber = invoiceNumber;
  }

  @JsonProperty( "invoice_nr" )
  private String  invoiceNumber;

  private Integer netto;

  private Integer brutto;

  private String  status;

  @JsonProperty( "period_from" )
  private Date    from;

  @JsonProperty( "period_until" )
  private Date    until;

  private String  currency;

  @JsonProperty( "vat_rate" )
  private Integer vatRate;

  @JsonProperty( "billing_date" )
  private Date    billingDate;

  @JsonProperty( "invoice_type" )
  private String  invoiceType;

  @JsonProperty( "last_reminder_date" )
  private Date    lastReminderDate;

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public void setInvoiceNumber( String invoiceNumber ) {
    this.invoiceNumber = invoiceNumber;
  }

  /**
   * @return formatted netto amount
   */
  public Integer getNetto() {
    return netto;
  }

  public void setNetto( Integer netto ) {
    this.netto = netto;
  }

  /**
   * @return formatted brutto amount
   */
  public Integer getBrutto() {
    return brutto;
  }

  public void setBrutto( Integer brutto ) {
    this.brutto = brutto;
  }

  /**
   * The invoice status (e.g. sent, trx_ok, trx_failed, invalid_payment, success, 1st_reminder, 2nd_reminder, 3rd_reminder,
   * suspend, canceled, transferred)
   * @return the invoice status
   */
  public String getStatus() {
    return status;
  }

  public void setStatus( String status ) {
    this.status = status;
  }

  /**
   * @return the start of this invoice period
   */
  public Date getFrom() {
    return from;
  }

  public void setFrom( Date from ) {
    this.from = from;
  }

  /**
   * @return the end of this invoice period
   */
  public Date getUntil() {
    return until;
  }

  public void setUntil( Date until ) {
    this.until = until;
  }

  /**
   * @return ISO 4217 formatted currency code.
   */
  public String getCurrency() {
    return currency;
  }

  public void setCurrency( String currency ) {
    this.currency = currency;
  }

  /**
   * @return VAT rate of the brutto amount
   */
  public Integer getVatRate() {
    return vatRate;
  }

  public void setVatRate( Integer vatRate ) {
    this.vatRate = vatRate;
  }

  /**
   * @return the billing date
   */
  public Date getBillingDate() {
    return billingDate;
  }

  public void setBillingDate( Date billingDate ) {
    this.billingDate = billingDate;
  }

  /**
   * The type: paymill, wirecard, acceptance etc. Indicates if it's a PAYMILL invoice or an acquirer payout.
   * @return the type
   */
  public String getInvoiceType() {
    return invoiceType;
  }

  public void setInvoiceType( String invoiceType ) {
    this.invoiceType = invoiceType;
  }

  /**
   * @return the last payment reminder
   */
  public Date getLastReminderDate() {
    return lastReminderDate;
  }

  public void setLastReminderDate( Date lastReminderDate ) {
    this.lastReminderDate = lastReminderDate;
  }

}

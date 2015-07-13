package com.paymill.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A shopping cart item object belongs to exactly one transaction. It represents the merchants item which will be given to paypal.
 * 
 * @author Vassil Nikolov
 * @since 5.1.0
 */
public class ShoppingCartItem {

  private String name;

  private String description;

  private Integer amount;

  private Integer quantity;

  @JsonProperty("item_number")
  private String itemNumber;

  private String url;

  /**
   * Item name, max. 127 characters
   * 
   * @return {@link String}
   */
  public String getName() {
    return name;
  }

  /**
   * Item name, max. 127 characters
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Additional description, max. 127 characters
   * 
   * @return {@link String}
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Additional description, max. 127 characters
   * 
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Price > 0 for a single item, including tax, can also be negative to act as a discount
   * 
   * @return {@link Integer}
   */
  public Integer getAmount() {
    return this.amount;
  }

  /**
   * Price > 0 for a single item, including tax, can also be negative to act as a discount
   * 
   * @param amount
   */
  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  /**
   * Quantity of this item
   * 
   * @return {@link Integer} > 0)
   */
  public Integer getQuantity() {
    return this.quantity;
  }

  /**
   * Quantity of this item
   * 
   * @param quantity
   *          {@link Integer} > 0
   */
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * Item number or other identifier (SKU/EAN), max. 127 characters
   * 
   * @return {@link String}
   */
  public String getItemNumber() {
    return this.itemNumber;
  }

  /**
   * Item number or other identifier (SKU/EAN), max. 127 characters
   * 
   * @param itemNumber
   */
  public void setItemNumber(String itemNumber) {
    this.itemNumber = itemNumber;
  }

  /**
   * URL of the item in your store, max. 2000 characters.
   * 
   * @return {@link String}
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * URL of the item in your store, max. 2000 characters
   * 
   * @param url
   */
  public void setUrl(String url) {
    this.url = url;
  }

}

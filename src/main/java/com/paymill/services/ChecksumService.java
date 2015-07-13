package com.paymill.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.paymill.models.Address;
import com.paymill.models.Checksum;
import com.paymill.models.Fee;
import com.paymill.models.ShoppingCartItem;
import com.paymill.utils.HttpClient;
import com.paymill.utils.ParameterMap;

/**
 * The {@link ChecksumService} is used to create PAYMILL {@link Checksum}s. In order to create client-side transactions, e.g.
 * PayPal.
 * 
 * @author Vassil Nikolov
 * @since 5.1.0
 */
public class ChecksumService extends AbstractService {

  private ChecksumService(HttpClient httpClient) {
    super(httpClient);
  }

  private final static String PATH = "/checksums";

  /**
   * Created a {@link Checksum} of {@link Checksum.Type} with amount in the given currency.
   * 
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param returnUrl
   *          URL to redirect customers to after checkout has completed.
   * @param cancelUrl
   *          URL to redirect customers to after they have canceled the checkout. As a result, there will be no transaction.
   * @return {@link Checksum} object.
   */
  public Checksum createChecksumForPaypal(Integer amount, String currency, String returnUrl, String cancelUrl) {
    return this.createChecksumForPaypalWithDescription(amount, currency, returnUrl, cancelUrl, null);
  }

  /**
   * Created a {@link Checksum} of {@link Checksum.Type} with amount in the given currency.
   * 
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param returnUrl
   *          URL to redirect customers to after checkout has completed.
   * @param cancelUrl
   *          URL to redirect customers to after they have canceled the checkout. As a result, there will be no transaction.
   * @param fee
   *          A {@link Fee}.
   * @param appId 
   *          App (ID) that created this refund or null if created by yourself.
   * @return {@link Checksum} object.
   */
  public Checksum createChecksumForPaypalWithFee(Integer amount, String currency, String returnUrl, String cancelUrl,
      Fee fee, String appId) {
    return this.createChecksumForPaypalWithFeeAndDescription(amount, currency, returnUrl, cancelUrl, fee, null, appId);
  }

  /**
   * Created a {@link Checksum} of {@link Checksum.Type} with amount in the given currency.
   * 
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param returnUrl
   *          URL to redirect customers to after checkout has completed.
   * @param cancelUrl
   *          URL to redirect customers to after they have canceled the checkout. As a result, there will be no transaction.
   * @param description
   *          A short description for the transaction.
   * @return {@link Checksum} object.
   */
  public Checksum createChecksumForPaypalWithDescription(Integer amount, String currency, String returnUrl,
      String cancelUrl, String description) {
    return this.createChecksumForPaypalWithItemsAndAddress(amount, currency, returnUrl, cancelUrl, description, null,
        null, null);
  }

  /**
   * Created a {@link Checksum} of {@link Checksum.Type} with amount in the given currency.
   * 
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param returnUrl
   *          URL to redirect customers to after checkout has completed.
   * @param cancelUrl
   *          URL to redirect customers to after they have canceled the checkout. As a result, there will be no transaction.
   * @param fee
   *          A {@link Fee}.
   * @param description
   *          A short description for the transaction.
   * @param appId 
   *          App (ID) that created this refund or null if created by yourself.
   * @return {@link Checksum} object.
   */
  public Checksum createChecksumForPaypalWithFeeAndDescription(Integer amount, String currency, String returnUrl,
      String cancelUrl, Fee fee, String description, String appId) {
    return this.createChecksumForPaypalWithFeeAndItemsAndAddress(amount, currency, returnUrl, cancelUrl, fee,
        description, null, null, null, appId);
  }

  /**
   * Created a {@link Checksum} of {@link Checksum.Type} with amount in the given currency.
   * 
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param returnUrl
   *          URL to redirect customers to after checkout has completed.
   * @param cancelUrl
   *          URL to redirect customers to after they have canceled the checkout. As a result, there will be no transaction.
   * @param description
   *          A short description for the transaction or <code>null</code>.
   * @param items
   *          {@link List} of {@link ShoppingCartItem}s
   * @param billing
   *          Billing {@link Address} for this transaction.
   * @param shipping
   *          Shipping {@link Address} for this transaction.
   * @return {@link Checksum} object.
   */
  public Checksum createChecksumForPaypalWithItemsAndAddress(Integer amount, String currency, String returnUrl,
      String cancelUrl, String description, List<ShoppingCartItem> items, Address shipping, Address billing) {
    return this.createChecksumForPaypalWithFeeAndItemsAndAddress(amount, currency, returnUrl, cancelUrl, null,
        description, items, shipping, billing, null);
  }

  /**
   * Created a {@link Checksum} of {@link Checksum.Type} with amount in the given currency.
   * 
   * @param amount
   *          Amount (in cents) which will be charged.
   * @param currency
   *          ISO 4217 formatted currency code.
   * @param returnUrl
   *          URL to redirect customers to after checkout has completed.
   * @param cancelUrl
   *          URL to redirect customers to after they have canceled the checkout. As a result, there will be no transaction.
   * @param fee
   *          A {@link Fee}.
   * @param description
   *          A short description for the transaction or <code>null</code>.
   * @param items
   *          {@link List} of {@link ShoppingCartItem}s
   * @param billing
   *          Billing {@link Address} for this transaction.
   * @param shipping
   *          Shipping {@link Address} for this transaction.
   * @param appId 
   *          App (ID) that created this refund or null if created by yourself.
   * @return {@link Checksum} object.
   */
  private Checksum createChecksumForPaypalWithFeeAndItemsAndAddress(Integer amount, String currency, String returnUrl,
      String cancelUrl, Fee fee, String description, List<ShoppingCartItem> items, Address shipping, Address billing, String appId) {
    ValidationUtils.validatesAmount(amount);
    ValidationUtils.validatesCurrency(currency);
    ValidationUtils.validatesUrl(cancelUrl);
    ValidationUtils.validatesUrl(returnUrl);
    ValidationUtils.validatesFee(fee);
    
    ParameterMap<String, String> params = new ParameterMap<String, String>();
    
    params.add("checksum_type", "paypal");
    params.add("amount", String.valueOf(amount));
    params.add("currency", currency);
    params.add("cancel_url", cancelUrl);
    params.add("return_url", returnUrl);

    if (StringUtils.isNotBlank(description))
      params.add("description", description);
    if (fee != null && fee.getAmount() != null)
      params.add("fee_amount", String.valueOf(fee.getAmount()));
    if (fee != null && StringUtils.isNotBlank(fee.getPayment()))
      params.add("fee_payment", fee.getPayment());
    if (fee != null && StringUtils.isNotBlank(fee.getCurrency()))
      params.add("fee_currency", fee.getCurrency());
    if (StringUtils.isNotBlank(appId))
      params.add("app_id", appId);

    this.parametrizeItems(items, params);
    this.parametrizeAddress(billing, params, "billing_address");
    this.parametrizeAddress(shipping, params, "shipping_address");

    return RestfulUtils.create(ChecksumService.PATH, params, Checksum.class, super.httpClient);
  }

  private void parametrizeItems(List<ShoppingCartItem> items, ParameterMap<String, String> params) {
    if (items != null) {
      for (int i = 0; i < items.size(); i++) {
        if (StringUtils.isNotBlank(items.get(i).getName()))
          params.add("items[" + i + "][name]", items.get(i).getName());
        if (StringUtils.isNotBlank(items.get(i).getDescription()))
          params.add("items[" + i + "][description]", items.get(i).getDescription());
        if (items.get(i).getAmount() != null)
          params.add("items[" + i + "][amount]", String.valueOf(items.get(i).getAmount()));
        if (items.get(i).getQuantity() != null)
          params.add("items[" + i + "][quantity]", String.valueOf(items.get(i).getQuantity()));
        if (StringUtils.isNotBlank(items.get(i).getItemNumber()))
          params.add("items[" + i + "][item_number]", items.get(i).getItemNumber());
        if (StringUtils.isNotBlank(items.get(i).getUrl()))
          params.add("items[" + i + "][url]", items.get(i).getUrl());
      }
    }
  }

  private void parametrizeAddress(Address address, ParameterMap<String, String> params, String prefix) {
    if (address != null) {
      if (StringUtils.isNotBlank(address.getName()))
        params.add(prefix + "[name]", address.getName());
      if (StringUtils.isNotBlank(address.getStreetAddress()))
        params.add(prefix + "[street_address]", address.getStreetAddress());
      if (StringUtils.isNotBlank(address.getStreetAddressAddition()))
        params.add(prefix + "[street_address_addition]", address.getStreetAddressAddition());
      if (StringUtils.isNotBlank(address.getCity()))
        params.add(prefix + "[city]", address.getCity());
      if (StringUtils.isNotBlank(address.getState()))
        params.add(prefix + "[state]", address.getState());
      if (StringUtils.isNotBlank(address.getPostalCode()))
        params.add(prefix + "[postal_code]", address.getPostalCode());
      if (StringUtils.isNotBlank(address.getCountry()))
        params.add(prefix + "[country]", address.getCountry());
      if (StringUtils.isNotBlank(address.getPhone()))
        params.add(prefix + "[phone]", address.getPhone());
    }
  }

}

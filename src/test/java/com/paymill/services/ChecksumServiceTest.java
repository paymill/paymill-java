package com.paymill.services;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.models.Address;
import com.paymill.models.Checksum;
import com.paymill.models.Fee;
import com.paymill.models.ShoppingCartItem;

public class ChecksumServiceTest {

  private int    amount      = 9700;
  private String currency    = "USD";
  private String returnUrl   = "http://www.return.com";
  private String cancelUrl   = "http://www.cancel.com";
  private String description = "Bebemen is cool";

  private List<ShoppingCartItem> items           = null;
  private Address                shippingAddress = null;
  private Address                billingAddress  = null;

  private Fee     fee;
  private Integer feeAmount  = 200;
  private String  feePayment = "pay_3af44644dd6d25c820a8";

  private ChecksumService checksumService;

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( System.getProperty( "apiKey" ) );
    this.checksumService = paymill.getChecksumService();

    this.items = new ArrayList<ShoppingCartItem>();
    this.items.add(this.createShopingCardItem( "Rambo Poster", "John J. Rambo", 2200, 3, "898-24342-343",
        "http://www.store.com/items/posters/12121-rambo" ));
    this.items.add(this.createShopingCardItem("Comando Poster", "John Matrix", 3100, 1, "898-24342-341",
        "http://www.store.com/items/posters/12121-comando"));

    this.billingAddress = this.createAddress("John Rambo", "TH", "Buriram", "Buriram", "Wat Sawai So 2", "23/4/14",
        "1527", "+66 32 12-555-23");
    this.shippingAddress = this.createAddress("Rocky Balboa", "US", "Pennsylvania", "Philadelphia",
        "1818 East Tusculum Street", "34/2B", "19134", "+1 215 23-555-32");

    this.fee = new Fee();
    this.fee.setAmount(this.feeAmount);
    this.fee.setPayment(this.feePayment);

  }

  @AfterClass
  public void tearDown() {
    this.checksumService = null;

    this.items = null;
  }

  @Test
  public void testCreate_WithMandatoryParameters_shouldSucceed() throws UnsupportedEncodingException {
    Checksum checksum = this.checksumService.createChecksumForPaypal(this.amount, this.currency, this.returnUrl,
        this.cancelUrl);
    this.validateChecksum(checksum);
  }

  @Test
  public void testCreate_WithDescriptionParameters_shouldSucceed() throws UnsupportedEncodingException {
    Checksum checksum = this.checksumService.createChecksumForPaypalWithDescription(this.amount, this.currency,
        this.returnUrl, this.cancelUrl, this.description);
    this.validateChecksum(checksum);
    Assert.assertTrue(checksum.getData().contains(URLEncoder.encode(this.description, "UTF-8")));
  }

  @Test
  public void testCreate_WithItemsParameters_shouldSucceed() throws UnsupportedEncodingException {
    Checksum checksum = this.checksumService.createChecksumForPaypalWithItemsAndAddress(this.amount, this.currency,
        this.returnUrl, this.cancelUrl, this.description, this.items, null, null);
    this.validateChecksum(checksum);
    Assert.assertTrue(checksum.getData().contains(URLEncoder.encode(this.description, "UTF-8")));
    Assert.assertTrue(URLDecoder.decode(checksum.getData(), "UTF-8").contains(
        "items[0][name]=Rambo Poster&items[0][description]=John J. Rambo&items[0][quantity]=3&items[0][amount]=2200&items[0][item_number]=898-24342-343&items[0][url]=http://www.store.com/items/posters/12121-rambo&items[1][url]=http://www.store.com/items/posters/12121-comando&items[1][description]=John Matrix&items[1][quantity]=1&items[1][amount]=3100&items[1][name]=Comando Poster&items[1][item_number]=898-24342-341&amount=9700&description=Bebemen is cool&return_url=http://www.return.com&currency=USD&cancel_url=http://www.cancel.com"));
  }

  @Test
  public void testCreate_WithBillingAddressParameters_shouldSucceed() throws UnsupportedEncodingException {
    Checksum checksum = this.checksumService.createChecksumForPaypalWithItemsAndAddress(this.amount, this.currency,
        this.returnUrl, this.cancelUrl, this.description, this.items, null, this.billingAddress);
    this.validateChecksum(checksum);
    Assert.assertTrue(checksum.getData().contains(URLEncoder.encode(this.description, "UTF-8")));
    Assert.assertTrue(URLDecoder.decode(checksum.getData(), "UTF-8").contains(
        "billing_address[postal_code]=1527&billing_address[name]=John Rambo&billing_address[country]=TH&billing_address[city]=Buriram&billing_address[phone]=+66 32 12-555-23&billing_address[street_address]=Wat Sawai So 2&billing_address[state]=Buriram&billing_address[street_address_addition]=23/4/14"));
  }

  @Test
  public void testCreate_WithShippingAddressParameters_shouldSucceed() throws UnsupportedEncodingException {
    Checksum checksum = this.checksumService.createChecksumForPaypalWithItemsAndAddress(this.amount, this.currency,
        this.returnUrl, this.cancelUrl, this.description, this.items, this.shippingAddress, null);
    this.validateChecksum(checksum);
    Assert.assertTrue(checksum.getData().contains(URLEncoder.encode(this.description, "UTF-8")));
    Assert.assertTrue(URLDecoder.decode(checksum.getData(), "UTF-8").contains(
        "shipping_address[postal_code]=19134&shipping_address[city]=Philadelphia&shipping_address[country]=US&shipping_address[name]=Rocky Balboa&shipping_address[phone]=+1 215 23-555-32&shipping_address[state]=Pennsylvania&shipping_address[street_address]=1818 East Tusculum Street&shipping_address[street_address_addition]=34/2B"));
  }

  @Test
  public void testCreate_WithFee_shouldSucceed() throws UnsupportedEncodingException {
    Checksum checksum = this.checksumService.createChecksumForPaypalWithFee(this.amount, this.currency, this.returnUrl,
        this.cancelUrl, this.fee, "app_fake");
    this.validateChecksum(checksum);
    Assert.assertTrue(URLDecoder.decode(checksum.getData(), "UTF-8").contains(""));
  }

  private void validateChecksum(Checksum checksum) throws UnsupportedEncodingException {
    Assert.assertNotNull(checksum);

    Assert.assertTrue(checksum.getId().startsWith("chk_"));
    Assert.assertEquals(checksum.getType(), "paypal");
    Assert.assertEquals(checksum.getChecksum().length(), 128);
    Assert.assertNull(checksum.getAppId());
    Assert.assertNotNull(checksum.getCreatedAt());
    Assert.assertNotNull(checksum.getUpdatedAt());

    Assert.assertTrue(checksum.getData().contains(String.valueOf(this.amount)));
    Assert.assertTrue(checksum.getData().contains(this.currency));
    Assert.assertTrue(checksum.getData().contains(URLEncoder.encode(this.cancelUrl, "UTF-8")));
    Assert.assertTrue(checksum.getData().contains(URLEncoder.encode(this.returnUrl, "UTF-8")));
  }

  private ShoppingCartItem createShopingCardItem(String name, String description, int amount, int quantity,
      String itemNumber, String url) {
    ShoppingCartItem item = new ShoppingCartItem();

    item.setName(name);
    item.setDescription(description);
    item.setAmount(amount);
    item.setQuantity(quantity);
    item.setItemNumber(itemNumber);
    item.setUrl(url);

    return item;
  }

  private Address createAddress(String name, String country, String state, String city, String streetAddress,
      String streetAddressAddition, String postalCode, String phone) {
    Address address = new Address();

    address.setName(name);
    address.setCountry(country);
    address.setState(state);
    address.setCity(city);
    address.setStreetAddress(streetAddress);
    address.setStreetAddressAddition(streetAddressAddition);
    address.setPostalCode(postalCode);
    address.setPhone(phone);

    return address;
  }

}

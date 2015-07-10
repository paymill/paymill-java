package com.paymill.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.models.Refund;
import com.paymill.models.Transaction;

import com.paymill.utils.HttpClient;
import com.paymill.utils.JerseyClient;

public class RefundServiceTest {

  private String             token       = null;
  private Integer            amount      = 2800;
  private String             description = "Boom, boom, shake the room!";

  private Transaction        transaction;
  private Refund             refund;

  private TransactionService transactionService;
  private RefundService      refundService;

  private Transaction        lastTransaction;

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( System.getProperty( "apiKey" ) );
    this.refundService = paymill.getRefundService();
    this.transactionService = paymill.getTransactionService();
  }

  @BeforeMethod
  public void setToken() {
    HttpClient httpClient = new JerseyClient( "941569045353c8ac2a5689deb88871bb", 0 );
    String content = httpClient.get("https://test-token.paymill.com/?transaction.mode=CONNECTOR_TEST&channel.id=941569045353c8ac2a5689deb88871bb&jsonPFunction=paymilljstests&account.number=4111111111111111&account.expiry.month=12&account.expiry.year=2015&account.verification=123&account.holder=Max%20Mustermann&presentation.amount3D=2800&presentation.currency3D=EUR");

    Pattern pattern = Pattern.compile( "(tok_)[a-z|0-9]+" );
    Matcher matcher = pattern.matcher( content );

    if( matcher.find() ) {
      this.token = matcher.group();
      this.transaction = this.transactionService.createWithToken( this.token, this.amount, "EUR" );
    }
  }

  @Test
  public void testRefundTransaction_WithoutDescruption_shouldSucceed() {
    Refund refund = this.refundService.refundTransaction( this.transaction, 1000 );
    this.validatesRefund( refund );
    Assert.assertNull( refund.getDescription() );
  }

  @Test
  public void testRefundTransaction_WithDescruption_shouldSucceed() {
    this.refund = this.refundService.refundTransaction( this.transaction, 1000, this.description );
    this.validatesRefund( this.refund );
    Assert.assertEquals( this.refund.getDescription(), this.description );
    this.lastTransaction = this.transaction;
  }

  @Test( dependsOnMethods = "testRefundTransaction_WithDescruption_shouldSucceed" )
  public void testShow_shouldSucceed() {
    this.transaction = this.lastTransaction;
    this.refund = this.refundService.get( this.refund );
    this.validatesRefund( this.refund );
    Assert.assertEquals( this.refund.getDescription(), this.description );
  }

  private void validatesRefund( final Refund refund ) {
    Assert.assertNotNull( refund );
    Assert.assertNotNull( refund.getId() );
    Assert.assertEquals( refund.getAmount(), Integer.valueOf( 1000 ) );
    Assert.assertEquals( refund.getStatus(), Refund.Status.REFUNDED );
    Assert.assertFalse( refund.getLivemode() );
    Assert.assertNotNull( refund.getCreatedAt() );
    Assert.assertNotNull( refund.getUpdatedAt() );
    Assert.assertNull( refund.getAppId() );
    Assert.assertEquals( refund.getTransaction().getId(), this.transaction.getId() );
    Assert.assertEquals( refund.getTransaction().getStatus(), Transaction.Status.PARTIAL_REFUNDED );
    Assert.assertEquals( refund.getResponseCode(), Integer.valueOf( 20000 ) );
  }

}

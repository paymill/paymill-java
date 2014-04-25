package com.paymill.services;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.models.Refund;
import com.paymill.models.Transaction;

public class RefundServiceTest {

  private String             token       = "098f6bcd4621d373cade4e832627b4f6";
  private Integer            amount      = 2800;
  private String             description = "Boom, boom, shake the room!";

  private Transaction        transaction;
  private Refund             refund;

  private TransactionService transactionService;
  private RefundService      refundService;

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( System.getProperty( "apiKey" ) );
    this.refundService = paymill.getRefundService();
    this.transactionService = paymill.getTransactionService();
    this.transaction = this.transactionService.createWithToken( this.token, this.amount, "EUR" );
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
  }

  @Test
  public void testShow_shouldSucceed() {
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

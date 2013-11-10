package com.paymill.services;

import org.apache.commons.lang3.BooleanUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.Paymill;
import com.paymill.models.Fee;
import com.paymill.models.Transaction;

public class TransactionServiceTest {

  private String             token       = "098f6bcd4621d373cade4e832627b4f6";
  private int                amount      = 3201;
  private String             currency    = "EUR";
  private String             description = "Boom, boom, shake the room";
  private Integer            feeAmount   = 200;
  private String             feePayment  = "pay_3af44644dd6d25c820a8";

  private Transaction        transaction;
  private Fee                fee;

  private TransactionService transactionService;

  @BeforeClass
  public void setUp() {
    this.fee = new Fee();
    this.fee.setAmount( feeAmount );
    this.fee.setPayment( feePayment );

    Paymill.setApiKey( "255de920504bd07dad2a0bf57822ee40" );
    this.transactionService = Paymill.getService( TransactionService.class );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Token can not be blank" )
  public void testCreateWithToken_TokenIsNull_shouldFail() {
    this.transactionService.createWithToken( null, amount, currency, description );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Amount can not be blank or negative" )
  public void testCreateWithToken_AmountIsNull_shouldFail() {
    this.transactionService.createWithTokenAndFee( token, null, currency, description, null );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Amount can not be blank or negative" )
  public void testCreateWithToken_AmountIsNegative_shouldFail() {
    this.transactionService.createWithToken( token, -1, currency, description );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Currency can not be blank" )
  public void testCreateWithToken_CurrencyIsNull_shouldFail() {
    this.transactionService.createWithToken( token, amount, null, description );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "When fee payment is given, fee amount is mandatory" )
  public void testCreateWithToken_FeeAmountIsNullAndFeePaymentIsNotNull_shouldFail() {
    Fee fee = new Fee();
    fee.setPayment( feePayment );
    this.transactionService.createWithTokenAndFee( token, amount, currency, description, fee );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "When fee amount is given, fee payment is mandatory" )
  public void testCreateWithToken_FeeAmountIsNotNullButFeePaymentIsNull_shouldFail() {
    Fee fee = new Fee();
    fee.setAmount( feeAmount );
    this.transactionService.createWithTokenAndFee( token, amount, currency, description, fee );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Fee amount can not be negative" )
  public void testCreateWithToken_FeeAmountIsNegative_shouldFail() {
    Fee fee = new Fee();
    fee.setAmount( -100 );
    fee.setPayment( feePayment );
    this.transactionService.createWithTokenAndFee( token, amount, currency, description, fee );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Fee payment should statrt with 'pay_' prefix" )
  public void testCreateWithToken_FeePayment_shouldFail() {
    Fee fee = new Fee();
    fee.setAmount( feeAmount );
    fee.setPayment( "tran_3af44644dd6d25c820a8" );
    this.transactionService.createWithTokenAndFee( token, amount, currency, description, fee );
  }

  @Test
  public void testCreateWithToken_WithDescruption_shouldSucceed() {
    Transaction transaction = this.transactionService.createWithToken( token, amount, currency, description );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getDescription(), this.description );

    this.transaction = transaction;
  }

  @Test
  public void testCreateWithToken_WithoutDescruption_shouldSucceed() {
    Transaction transaction = this.transactionService.createWithToken( token, amount, currency );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getDescription(), null );
  }

  //  @Test
  public void testCreateWithToken_FeeAsString_shouldSucceed() {
    Paymill.refreshApiKey( "255de920504bd07dad2a0bf57822ee40" );
    Transaction transaction = this.transactionService.createWithTokenAndFee( token, amount, currency, null, fee );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getPayment().getId(), this.feePayment );
    Assert.assertEquals( transaction.getDescription(), null );
  }

  private Transaction validateTransaction( Transaction transaction ) {
    Assert.assertNotNull( transaction );
    Assert.assertNotNull( transaction.getId() );
    Assert.assertEquals( transaction.getAmount().intValue(), this.amount );
    Assert.assertEquals( transaction.getOriginAmount(), Integer.valueOf( this.amount ) );
    Assert.assertEquals( transaction.getCurrency(), this.currency );
    Assert.assertEquals( transaction.getStatus(), Transaction.Status.CLOSED );
    Assert.assertTrue( BooleanUtils.isFalse( transaction.getLivemode() ) );
    Assert.assertNull( transaction.getRefunds() );
    Assert.assertEquals( transaction.getCurrency(), this.currency );
    Assert.assertNotNull( transaction.getCreatedAt() );
    Assert.assertNotNull( transaction.getUpdatedAt() );
    Assert.assertTrue( BooleanUtils.isFalse( transaction.getFraud() ) );
    Assert.assertNotNull( transaction.getPayment() );
    Assert.assertNotNull( transaction.getClient() );
    Assert.assertNull( transaction.getPreauthorization() );
    Assert.assertTrue( transaction.getFees().isEmpty() );
    Assert.assertNull( transaction.getAppId() );
    return transaction;
  }

  @Test( dependsOnMethods = "testCreateWithToken_WithDescruption_shouldSucceed" )
  public void testShow_shouldSucceed() {
    Transaction transaction = this.transactionService.show( this.transaction );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getDescription(), this.description );
  }

  @Test( dependsOnMethods = "testCreateWithToken_WithDescruption_shouldSucceed" )
  public void testUpdate_shouldSucceed() {
    this.transaction.setDescription( "Boom, boom, update the room" );
    this.transaction.setAmount( 5555 );
    this.transaction = this.transactionService.update( this.transaction );
    this.validateTransaction( this.transaction );
    Assert.assertEquals( transaction.getDescription(), "Boom, boom, update the room" );
    Assert.assertEquals( this.transaction.getAmount(), Integer.valueOf( this.amount ) );
  }

}

package com.paymill.services;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.models.Fee;
import com.paymill.models.Payment;
import com.paymill.models.PaymillList;
import com.paymill.models.Preauthorization;
import com.paymill.models.Transaction;

import com.paymill.utils.HttpClient;
import com.paymill.utils.JerseyClient;

public class TransactionServiceTest {

  private String                  token       = null;
  private int                     amount      = 3201;
  private String                  currency    = "EUR";
  private String                  description = "Boom, boom, shake the room";
  private Integer                 feeAmount   = 200;
  private String                  feePayment  = "pay_3af44644dd6d25c820a8";

  private Preauthorization        preauthorization;
  private Transaction             transaction;
  private Fee                     fee;
  private Payment                 payment;

  private TransactionService      transactionService;
  private PreauthorizationService preauthorizationService;
  private PaymentService          paymentService;

  @BeforeClass
  public void setUp() {
    this.fee = new Fee();
    this.fee.setAmount( this.feeAmount );
    this.fee.setPayment( this.feePayment );

    PaymillContext paymill = new PaymillContext( System.getProperty( "apiKey" ) );
    this.transactionService = paymill.getTransactionService();
    this.preauthorizationService = paymill.getPreauthorizationService();
    this.paymentService = paymill.getPaymentService();
  }

  @BeforeMethod
  public void setToken() {
    HttpClient httpClient = new JerseyClient( "941569045353c8ac2a5689deb88871bb", 0 );
    String content = httpClient.get("https://test-token.paymill.com/?transaction.mode=CONNECTOR_TEST&channel.id=941569045353c8ac2a5689deb88871bb&jsonPFunction=paymilljstests&account.number=4111111111111111&account.expiry.month=12&account.expiry.year=2015&account.verification=123&account.holder=Max%20Mustermann&presentation.amount3D=3201&presentation.currency3D=EUR");

    Pattern pattern = Pattern.compile( "(tok_)[a-z|0-9]+" );
    Matcher matcher = pattern.matcher( content );

    if( matcher.find() ) {
      this.token = matcher.group();

      this.preauthorization = this.preauthorizationService.createWithToken( this.token, this.amount, this.currency );
      this.payment = this.paymentService.createWithToken( this.token );
    }
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Token can not be blank" )
  public void testCreateWithToken_TokenIsNull_shouldFail() {
    this.transactionService.createWithToken( null, this.amount, this.currency, this.description );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Amount can not be blank or negative" )
  public void testCreateWithToken_AmountIsNull_shouldFail() {
    this.transactionService.createWithTokenAndFee( this.token, null, this.currency, this.description, null );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Amount can not be blank or negative" )
  public void testCreateWithToken_AmountIsNegative_shouldFail() {
    this.transactionService.createWithToken( this.token, -1, this.currency, this.description );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Currency can not be blank" )
  public void testCreateWithToken_CurrencyIsNull_shouldFail() {
    this.transactionService.createWithToken( this.token, this.amount, null, this.description );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "When fee payment is given, fee amount is mandatory" )
  public void testCreateWithToken_FeeAmountIsNullAndFeePaymentIsNotNull_shouldFail() {
    Fee fee = new Fee();
    fee.setPayment( this.feePayment );
    this.transactionService.createWithTokenAndFee( this.token, this.amount, this.currency, this.description, fee );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "When fee amount is given, fee payment is mandatory" )
  public void testCreateWithToken_FeeAmountIsNotNullButFeePaymentIsNull_shouldFail() {
    Fee fee = new Fee();
    fee.setAmount( this.feeAmount );
    this.transactionService.createWithTokenAndFee( this.token, this.amount, this.currency, this.description, fee );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Fee amount can not be negative" )
  public void testCreateWithToken_FeeAmountIsNegative_shouldFail() {
    Fee fee = new Fee();
    fee.setAmount( -100 );
    fee.setPayment( this.feePayment );
    this.transactionService.createWithTokenAndFee( this.token, this.amount, this.currency, this.description, fee );
  }

  @Test( expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Fee payment should statrt with 'pay_' prefix" )
  public void testCreateWithToken_FeePayment_shouldFail() {
    Fee fee = new Fee();
    fee.setAmount( this.feeAmount );
    fee.setPayment( "tran_3af44644dd6d25c820a8" );
    this.transactionService.createWithTokenAndFee( this.token, this.amount, this.currency, this.description, fee );
  }

  @Test
  public void testCreateWithToken_WithDescruption_shouldSucceed() {
    Transaction transaction = this.transactionService.createWithToken( this.token, this.amount, this.currency, this.description );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getDescription(), this.description );
    this.transaction = transaction;
  }

  @Test
  public void testCreateWithToken_WithoutDescription_shouldSucceed() {
    Transaction transaction = this.transactionService.createWithToken( this.token, this.amount, this.currency );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getDescription(), "" );
  }

  // @Test
  public void testCreateWithToken_FeeAsString_shouldSucceed() {
    Transaction transaction = this.transactionService.createWithTokenAndFee( this.token, this.amount, this.currency, null, this.fee );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getPayment().getId(), this.feePayment );
    Assert.assertEquals( transaction.getDescription(), "" );
  }

  private Transaction validateTransaction( final Transaction transaction ) {
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
    Assert.assertTrue( transaction.getFees().isEmpty() );
    Assert.assertNull( transaction.getAppId() );
    return transaction;
  }

  @Test( dependsOnMethods = "testCreateWithToken_WithDescruption_shouldSucceed" )
  public void testShow_shouldSucceed() {
    Transaction transaction = this.transactionService.get( this.transaction );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getDescription(), this.description );
  }

  @Test( dependsOnMethods = "testCreateWithToken_WithDescruption_shouldSucceed" )
  public void testUpdate_shouldSucceed() {
    this.transaction.setDescription( "Boom, boom, update the room" );
    this.transaction.setAmount( 5555 );
    this.transaction.setAppId( "fake" );
    this.transactionService.update( this.transaction );
    this.validateTransaction( this.transaction );
    Assert.assertEquals( this.transaction.getDescription(), "Boom, boom, update the room" );
    Assert.assertEquals( this.transaction.getAmount(), Integer.valueOf( this.amount ) );
  }

  @Test
  public void testCreateWithPreauthorization() {
    Transaction transaction = this.transactionService.createWithPreauthorization( this.preauthorization, this.amount, this.currency, this.description );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getDescription(), this.description );
    Assert.assertNotNull( transaction.getPreauthorization() );
  }

  @Test
  public void testCreateWithPayment() {
    Transaction transaction = this.transactionService.createWithPayment( this.payment, this.amount, this.currency, this.description );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getDescription(), this.description );
    Assert.assertEquals( transaction.getPayment().getId(), this.payment.getId() );
  }

  @Test( dependsOnMethods = "testCreateWithToken_WithDescruption_shouldSucceed" )
  public void testListWithFilterByExactCreatedAt() {
    Date createdAt = this.transaction.getCreatedAt();
    Transaction.Filter filter = Transaction.createFilter().byCreatedAt( createdAt, null );
    PaymillList<Transaction> wrapper = this.transactionService.list( filter, null );

    Assert.assertTrue( wrapper.getDataCount() >= 1 );
    Assert.assertFalse( wrapper.getData().isEmpty() );
  }

  @Test
  public void testListWithFilterByCreatedAtPeriod() throws ParseException {
    Date createdAt = this.transaction.getCreatedAt();
    Date from = DateUtils.addMinutes( createdAt, -1 );
    Date to = DateUtils.addMinutes( createdAt, 1 );

    Transaction.Filter filter = Transaction.createFilter().byCreatedAt( from, to );
    PaymillList<Transaction> wrapper = this.transactionService.list( filter, null );

    Assert.assertTrue( wrapper.getDataCount() >= 1 );
    Assert.assertFalse( wrapper.getData().isEmpty() );
  }

}

package com.paymill.services;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.Paymill;
import com.paymill.models.Client;
import com.paymill.models.Payment;
import com.paymill.models.PaymillList;

public class PaymentServiceTest {

  private String         token    = "098f6bcd4621d373cade4e832627b4f6";

  private Payment        paymentWithClient;
  private List<Payment>  payments = new ArrayList<Payment>();

  private PaymentService paymentService;
  private ClientService  clientService;
  private Client         client;

  @BeforeClass
  public void setUp() {
    Paymill paymill = new Paymill( "255de920504bd07dad2a0bf57822ee40" );

    this.paymentService = paymill.getPaymentService();
    this.clientService = paymill.getClientService();

    this.client = this.clientService.createWithEmailAndDescription( null, "Boom" );
  }

  @AfterClass
  public void tearDown() {
    for( Payment payment : this.payments ) {
      this.paymentService.delete( payment );
      if( payment.getClient() != null )
        this.clientService.delete( payment.getClient() );
    }

    //TODO[VNi]: There is an API error, creating a payment results in 2 payments in paymill
    PaymillList<Payment> wrapper = this.paymentService.list();
    for( Payment payment : wrapper.getData() ) {
      this.paymentService.delete( payment );
      if( payment.getClient() != null )
        this.clientService.delete( payment.getClient() );
    }
  }

  @Test
  public void testCreate_WithToken_shouldSecceed() {
    Payment payment = this.paymentService.createWithToken( this.token );

    Assert.assertNotNull( payment );
    Assert.assertNotNull( payment.getId() );
    Assert.assertNotNull( payment.getType() );
    Assert.assertNotNull( payment.getCreatedAt() );
    Assert.assertNotNull( payment.getUpdatedAt() );
    Assert.assertNull( payment.getAppId() );

    this.payments.add( payment );
  }

  @Test
  public void testCreate_WithTokenAndClient_shouldSecceed() {
    Payment payment = this.paymentService.createWithTokenAndClient( this.token, this.client );

    Assert.assertNotNull( payment );
    Assert.assertNotNull( payment.getId() );
    Assert.assertNotNull( payment.getType() );
    Assert.assertNotNull( payment.getCreatedAt() );
    Assert.assertNotNull( payment.getUpdatedAt() );
    Assert.assertNull( payment.getAppId() );

    this.paymentWithClient = payment;
    this.payments.add( payment );
  }

  @Test( dependsOnMethods = "testCreate_WithTokenAndClient_shouldSecceed" )
  public void testShow_shouldSucceed() {
    Payment payment = this.paymentService.get( this.paymentWithClient );
    Assert.assertNotNull( payment );
    Assert.assertNotNull( payment.getId() );
    Assert.assertNotNull( payment.getType() );
    Assert.assertNotNull( payment.getCreatedAt() );
    Assert.assertNotNull( payment.getUpdatedAt() );
    Assert.assertNull( payment.getAppId() );
    Assert.assertEquals( payment.getId(), this.paymentWithClient.getId() );
  }

  @Test( dependsOnMethods = "testShow_shouldSucceed" )
  public void testListOrderByCreatedAtDesc() throws InterruptedException {
    Payment.Order order = Payment.createOrder().byCreatedAt().asc();

    PaymillList<Payment> wrapper = this.paymentService.list( null, order );
    List<Payment> payments = wrapper.getData();

    //TODO[VNi]: There is an API error, creating a payment results in 2 payments in paymill
    Assert.assertNotNull( payments );
    Assert.assertFalse( payments.isEmpty() );
    Assert.assertEquals( payments.size(), this.payments.size() * 2 );
    Assert.assertNull( payments.get( 3 ).getClient() );
    Assert.assertEquals( payments.get( 1 ).getClient(), this.client.getId() );
  }

  @Test( dependsOnMethods = "testListOrderByCreatedAtDesc" )
  public void testListFilterByCardType() {
    Payment.Filter filter = Payment.createFilter().byCardType( Payment.CardType.VISA );

    PaymillList<Payment> wrapper = this.paymentService.list( filter, null );
    List<Payment> payments = wrapper.getData();

    Assert.assertNotNull( payments );
    Assert.assertFalse( payments.isEmpty() );
    Assert.assertEquals( this.payments.size(), payments.size() );
  }

}

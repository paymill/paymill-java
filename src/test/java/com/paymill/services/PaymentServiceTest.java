package com.paymill.services;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.Paymill;
import com.paymill.models.Payment;

public class PaymentServiceTest {

  private String         token    = "098f6bcd4621d373cade4e832627b4f6";
  private String         clientId = "client_88a388d9dd48f86c3136";

  private Payment        payment;

  private PaymentService paymentService;

  @BeforeClass
  public void setUp() {
    Paymill.setApiKey( "2bb9c4c3f0776ba75cfdc60020d7ea35" );
    this.paymentService = Paymill.getService( PaymentService.class );
    ClientService clientService = Paymill.getService( ClientService.class );
    this.clientId = clientService.create( null, "Boom" ).getId();
  }

  @Test
  public void testCreate_WithToken_shouldSecceed() {
    Payment payment = this.paymentService.createWithToken( token );

    Assert.assertNotNull( payment );
    Assert.assertNotNull( payment.getId() );
    Assert.assertNotNull( payment.getType() );
    Assert.assertNotNull( payment.getCreatedAt() );
    Assert.assertNotNull( payment.getUpdatedAt() );
    Assert.assertNull( payment.getAppId() );
  }

  @Test
  public void testCreate_WithTokenAndClient_shouldSecceed() {
    this.payment = this.paymentService.createWithTokenAndClient( token, clientId );

    Assert.assertNotNull( payment );
    Assert.assertNotNull( payment.getId() );
    Assert.assertNotNull( payment.getType() );
    Assert.assertNotNull( payment.getCreatedAt() );
    Assert.assertNotNull( payment.getUpdatedAt() );
    Assert.assertNull( payment.getAppId() );
  }

  @Test( dependsOnMethods = "testCreate_WithTokenAndClient_shouldSecceed" )
  public void testShow_shouldSucceed() {
    Payment payment = this.paymentService.show( this.payment );
    Assert.assertNotNull( payment );
    Assert.assertNotNull( payment.getId() );
    Assert.assertNotNull( payment.getType() );
    Assert.assertNotNull( payment.getCreatedAt() );
    Assert.assertNotNull( payment.getUpdatedAt() );
    Assert.assertNull( payment.getAppId() );
    Assert.assertEquals( payment.getId(), this.payment.getId() );
  }

  public void testDelete_shouldSecceed() {
    this.payment = this.paymentService.delete( this.payment );
    Assert.assertNull( this.payment );
  }

}

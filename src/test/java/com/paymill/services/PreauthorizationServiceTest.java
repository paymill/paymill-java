package com.paymill.services;

import org.apache.commons.lang3.BooleanUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.Paymill;
import com.paymill.models.Preauthorization;
import com.paymill.models.Transaction;

public class PreauthorizationServiceTest {

  private String                  token    = "098f6bcd4621d373cade4e832627b4f6";
  private Integer                 amount   = 4202;
  private String                  currency = "EUR";

  private Preauthorization        preauthorization;

  private PreauthorizationService preauthorizationService;

  @BeforeClass
  public void setUp() {
    Paymill.setApiKey( "255de920504bd07dad2a0bf57822ee40" );
    this.preauthorizationService = Paymill.getService( PreauthorizationService.class );
  }

  @Test
  public void testCreateWithToken_WithoutDescruption_shouldSucceed() {
    Transaction transaction = this.preauthorizationService.createWithToken( token, amount, currency );
    this.validateTransaction( transaction );
    Assert.assertEquals( transaction.getDescription(), null );
  }

  private Transaction validateTransaction( Transaction transaction ) {
    Assert.assertNotNull( transaction );
    Assert.assertNotNull( transaction.getId() );
    Assert.assertEquals( transaction.getAmount(), this.amount );
    Assert.assertEquals( transaction.getOriginAmount(), Integer.valueOf( this.amount ) );
    Assert.assertEquals( transaction.getCurrency(), this.currency );
    Assert.assertEquals( transaction.getStatus(), Transaction.Status.PREAUTH );
    Assert.assertTrue( BooleanUtils.isFalse( transaction.getLivemode() ) );
    Assert.assertNull( transaction.getRefunds() );
    Assert.assertEquals( transaction.getCurrency(), this.currency );
    Assert.assertNotNull( transaction.getCreatedAt() );
    Assert.assertNotNull( transaction.getUpdatedAt() );
    Assert.assertTrue( BooleanUtils.isFalse( transaction.getFraud() ) );
    Assert.assertNotNull( transaction.getPayment() );
    Assert.assertNotNull( transaction.getClient() );
    Assert.assertNotNull( transaction.getPreauthorization() );
    Assert.assertTrue( transaction.getFees().isEmpty() );
    Assert.assertNull( transaction.getAppId() );
    return transaction;
  }



}

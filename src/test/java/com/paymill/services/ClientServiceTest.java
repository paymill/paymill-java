package com.paymill.services;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.Paymill;
import com.paymill.PaymillException;
import com.paymill.models.Client;

public class ClientServiceTest {

  private String        email       = "john.rambo@qaiware.com";
  private String        description = "Boom, boom, shake the room";

  private ClientService clientService;
  private Client        client;

  @BeforeClass
  public void setUp() {
    Paymill.setApiKey( "2bb9c4c3f0776ba75cfdc60020d7ea35" );
    this.clientService = Paymill.getService( ClientService.class );
  }

  @Test
  public void testCreate_WithoutParameters_shouldSecceed() {
    Client client = this.clientService.create( null, null );
    Assert.assertNotNull( client );
    this.validateClient( client );
    Assert.assertNull( client.getEmail() );
    Assert.assertNull( client.getDescription() );
  }

  @Test
  public void testCreate_WithEmail_shouldSecceed() {
    Client client = this.clientService.create( this.email, null );
    this.validateClient( client );
    Assert.assertEquals( this.email, client.getEmail() );
    Assert.assertNull( client.getDescription() );
  }

  //  @Test
  public void testCreate_WithWrongEmail_shouldFail() {
    Client client = this.clientService.create( "wrong.email", null );
    Assert.assertNull( client );
  }

  @Test
  public void testCreate_WithDescription_shouldSecceed() {
    Client client = this.clientService.create( null, this.description );
    this.validateClient( client );
    Assert.assertNull( client.getEmail() );
    Assert.assertEquals( this.description, client.getDescription() );

    this.client = client;
  }

  @Test
  public void testCreate_WithEmailAndDescription_shouldSecceed() {
    Client client = this.clientService.create( this.email, this.description );
    this.validateClient( client );
    Assert.assertEquals( this.email, client.getEmail() );
    Assert.assertEquals( this.description, client.getDescription() );
  }

  private void validateClient( Client client ) {
    Assert.assertNotNull( client );
    Assert.assertNotNull( client.getId() );
    Assert.assertNotNull( client.getCreatedAt() );
    Assert.assertNotNull( client.getUpdatedAt() );
  }

  @Test( dependsOnMethods = "testCreate_WithDescription_shouldSecceed" )
  public void testShow_shouldSecceed() {
    Client client = this.clientService.show( this.client );
    this.validateClient( client );
    Assert.assertEquals( client.getDescription(), this.description );
  }

  @Test( dependsOnMethods = "testCreate_WithDescription_shouldSecceed" )
  public void testUpdate_shouldSecceed() {
    this.client.setEmail( "john.firstblood.rambo@qaiware.com" );
    this.client.setDescription( "Boom, boom, update the room" );
    this.client.setAppId( "First Blood" );
    this.client = this.clientService.update( this.client );

    this.validateClient( client );
    Assert.assertEquals( this.client.getEmail(), "john.firstblood.rambo@qaiware.com" );
    Assert.assertEquals( this.client.getDescription(), "Boom, boom, update the room" );
    Assert.assertNull( this.client.getAppId() );
  }

  @Test( dependsOnMethods = "testUpdate_shouldSecceed", expectedExceptions = PaymillException.class, expectedExceptionsMessageRegExp = "\"Client not found\"" )
  public void testDelete_shouldSecceed() {
    this.client = this.clientService.delete( this.client );
    this.clientService.show( this.client );
  }

}

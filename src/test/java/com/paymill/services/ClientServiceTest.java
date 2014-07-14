package com.paymill.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.exceptions.PaymillException;
import com.paymill.models.Client;
import com.paymill.models.PaymillList;

public class ClientServiceTest {

  private String        email       = "john.rambo@qaiware.com";
  private String        descEmail   = "zzz@example.com";
  private String        description = "Boom, boom, shake the room";

  private ClientService clientService;

  private List<Client>  clients     = new ArrayList<Client>();

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( System.getProperty( "apiKey" ) );
    this.clientService = paymill.getClientService();
  }

  @AfterClass
  public void tearDown() {
    for( Client client : this.clients ) {
      this.clientService.delete( client );
    }
  }

  @Test
  public void testCreate_WithoutParameters_shouldSucceed() {
    Client client = this.clientService.createWithEmailAndDescription( null, null );
    Assert.assertNotNull( client );
    this.validateClient( client );
    Assert.assertNull( client.getEmail() );
    Assert.assertNull( client.getDescription() );
    this.clients.add( client );
  }

  @Test
  public void testCreate_WithEmail_shouldSucceed() {
    Client client = this.clientService.createWithEmailAndDescription( this.email, null );
    this.validateClient( client );
    Assert.assertEquals( this.email, client.getEmail() );
    Assert.assertNull( client.getDescription() );
    this.clients.add( client );
  }

  @Test( expectedExceptions = PaymillException.class )
  public void testCreate_WithWrongEmail_shouldFail() {
    this.clientService.createWithEmailAndDescription( "wrong.email", null );
  }

  @Test
  public void testCreate_WithDescription_shouldSucceed() {
    Client client = this.clientService.createWithEmailAndDescription( null, this.description );
    this.validateClient( client );
    Assert.assertNull( client.getEmail() );
    Assert.assertEquals( this.description, client.getDescription() );
    this.clients.add( client );
  }

  @Test
  public void testCreate_WithEmailAndDescription_shouldSucceed() {
    Client client = this.clientService.createWithEmailAndDescription( this.email, this.description );
    this.validateClient( client );
    Assert.assertEquals( this.email, client.getEmail() );
    Assert.assertEquals( this.description, client.getDescription() );
    this.clients.add( client );
  }

  @Test
  public void testShow_shouldSucceed() {
    Client client = this.clientService.createWithEmailAndDescription( this.email, this.description );
    client.setAppId( "fake" );
    this.clientService.get( client );
    this.validateClient( client );
    Assert.assertNull( client.getAppId() );
    this.clients.add( client );
  }

  @Test
  public void testUpdate_shouldSucceed() {
    Client client = this.clientService.createWithEmailAndDescription( this.email, this.description );
    client.setEmail( "john.firstblood.rambo@qaiware.com" );
    client.setDescription( "Boom, boom, update the room" );
    client.setCreatedAt( DateUtils.addMonths( new Date(), 1 ) );
    this.clientService.update( client );
    this.validateClient( client );
    Assert.assertEquals( client.getEmail(), "john.firstblood.rambo@qaiware.com" );
    Assert.assertEquals( client.getDescription(), "Boom, boom, update the room" );
    Assert.assertNull( client.getAppId() );
    this.clients.add( client );
  }

  @Test
  public void testListOrderByEmailDesc() {
    clients.add( this.clientService.createWithEmailAndDescription( this.descEmail, this.description ) );
    clients.add( this.clientService.createWithEmailAndDescription( this.descEmail, this.description ) );
    Client.Order order = Client.createOrder().byEmail().desc();
    PaymillList<Client> wrapper = this.clientService.list( null, order );
    List<Client> clients = wrapper.getData();
    Assert.assertNotNull( clients );
    Assert.assertFalse( clients.isEmpty() );
    Assert.assertEquals( clients.get( 0 ).getEmail(), this.descEmail );
  }

  @Test( dependsOnMethods = "testListOrderByEmailDesc" )
  public void testListFilterByEmailAndCreatedAt() throws ParseException {
    Date startCreatedAt = DateUtils.addMinutes( new Date(), -1 );
    Date endCreatedAt = DateUtils.addMinutes( new Date(), 2 );

    Client.Filter filter = Client.createFilter().byEmail( this.descEmail ).byCreatedAt( startCreatedAt, endCreatedAt );

    PaymillList<Client> wrapper = this.clientService.list( filter, null );
    List<Client> clients = wrapper.getData();

    Assert.assertNotNull( clients );
    Assert.assertFalse( clients.isEmpty() );

    Assert.assertEquals( clients.get( 0 ).getEmail(), this.descEmail );
    Assert.assertEquals( clients.get( 1 ).getEmail(), this.descEmail );
    //Assert.assertEquals( clients.size(), 11 ); // with dev key
    Assert.assertEquals( clients.size(), 2 ); // with travis key
  }

  @Test
  public void testListFilterByStartCreatedAt() throws ParseException {
    Date startCreatedAt = DateUtils.addMonths( new Date(), 1 );
    Client.Filter filter = Client.createFilter().byCreatedAt( startCreatedAt, null );

    PaymillList<Client> wrapper = this.clientService.list( filter, null );
    List<Client> clients = wrapper.getData();

    Assert.assertNotNull( clients );
    Assert.assertTrue( clients.isEmpty() ); // with travis key
  }

  private void validateClient( final Client client ) {
    Assert.assertNotNull( client );
    Assert.assertNotNull( client.getId() );
    Assert.assertNotNull( client.getCreatedAt() );
    Assert.assertNotNull( client.getUpdatedAt() );
  }

}

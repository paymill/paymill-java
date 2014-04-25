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

  private String        email       = "zz.john.rambo@qaiware.com";
  private String        description = "Boom, boom, shake the room";

  private ClientService clientService;

  private List<Client>  clients     = new ArrayList<Client>();

  private Client        clientWithDescriptionAndEmail;

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
  public void testCreate_WithoutParameters_shouldSecceed() {
    Client client = this.clientService.createWithEmailAndDescription( null, null );
    Assert.assertNotNull( client );
    this.validateClient( client );
    Assert.assertNull( client.getEmail() );
    Assert.assertNull( client.getDescription() );
    this.clients.add( client );
  }

  @Test
  public void testCreate_WithEmail_shouldSecceed() {
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
  public void testCreate_WithDescription_shouldSecceed() {
    Client client = this.clientService.createWithEmailAndDescription( null, this.description );
    this.validateClient( client );
    Assert.assertNull( client.getEmail() );
    Assert.assertEquals( this.description, client.getDescription() );

    this.clientWithDescriptionAndEmail = client;
    this.clients.add( client );
  }

  @Test
  public void testCreate_WithEmailAndDescription_shouldSecceed() {
    Client client = this.clientService.createWithEmailAndDescription( this.email, this.description );
    this.validateClient( client );
    Assert.assertEquals( this.email, client.getEmail() );
    Assert.assertEquals( this.description, client.getDescription() );
    this.clients.add( client );
  }

  @Test( dependsOnMethods = "testCreate_WithDescription_shouldSecceed" )
  public void testShow_shouldSecceed() {
    this.clientWithDescriptionAndEmail.setAppId( "fake" );
    Client client = this.clientService.get( this.clientWithDescriptionAndEmail.getId() );
    this.validateClient( client );
    Assert.assertEquals( client.getDescription(), this.description );
  }

  @Test( dependsOnMethods = "testCreate_WithDescription_shouldSecceed" )
  public void testUpdate_shouldSecceed() {
    this.clientWithDescriptionAndEmail.setEmail( "zz.john.firstblood.rambo@qaiware.com" );
    this.clientWithDescriptionAndEmail.setDescription( "Boom, boom, update the room" );
    this.clientWithDescriptionAndEmail.setCreatedAt( new Date( System.currentTimeMillis() * 100 ) );
    this.clientService.update( this.clientWithDescriptionAndEmail );

    this.validateClient( this.clientWithDescriptionAndEmail );
    Assert.assertEquals( this.clientWithDescriptionAndEmail.getEmail(), "zz.john.firstblood.rambo@qaiware.com" );
    Assert.assertEquals( this.clientWithDescriptionAndEmail.getDescription(), "Boom, boom, update the room" );
    Assert.assertNull( this.clientWithDescriptionAndEmail.getAppId() );
  }

  @Test( dependsOnMethods = "testUpdate_shouldSecceed" )
  public void testListOrderByEmailDesc() {
    Client.Order order = Client.createOrder().byEmail().desc();

    PaymillList<Client> wrapper = this.clientService.list( null, order );
    List<Client> clients = wrapper.getData();

    Assert.assertNotNull( clients );
    Assert.assertFalse( clients.isEmpty() );
    Assert.assertEquals( clients.get( 0 ).getEmail(), "zz.john.rambo@qaiware.com" );
  }

  @Test( dependsOnMethods = "testListOrderByEmailDesc" )
  public void testListFilterByEmailAndCreatedAt() throws ParseException {
    Date startCreatedAt = DateUtils.parseDate( "2014-03-13", "yyyy-MM-dd" );
    Date endCreatedAt = DateUtils.parseDate( "2014-03-14", "yyyy-MM-dd" );
    Client.Filter filter = Client.createFilter().byEmail( "john.rambo@qaiware.com" ).byCreatedAt( startCreatedAt, endCreatedAt );

    PaymillList<Client> wrapper = this.clientService.list( filter, null );
    List<Client> clients = wrapper.getData();

    Assert.assertNotNull( clients );
    Assert.assertFalse( clients.isEmpty() );

    Assert.assertEquals( clients.get( 0 ).getEmail(), "john.rambo@qaiware.com" );
    Assert.assertEquals( clients.get( 1 ).getEmail(), "john.rambo@qaiware.com" );
    //Assert.assertEquals( clients.size(), 11 ); // with dev key
    Assert.assertEquals( clients.size(), 5 ); // with travis key
  }

  @Test( dependsOnMethods = "testListFilterByEmailAndCreatedAt" )
  public void testListFilterByStartCreatedAt() throws ParseException {
    Date startCreatedAt = new Date( 1394183537000L );
    Client.Filter filter = Client.createFilter().byCreatedAt( startCreatedAt, null );

    PaymillList<Client> wrapper = this.clientService.list( filter, null );
    List<Client> clients = wrapper.getData();

    Assert.assertNotNull( clients );
    //Assert.assertFalse( clients.isEmpty() ); // with dev key
    //Assert.assertEquals( clients.size(), 1 ); // with dev key
    Assert.assertTrue( clients.isEmpty() ); // with travis key
  }

  private void validateClient( final Client client ) {
    Assert.assertNotNull( client );
    Assert.assertNotNull( client.getId() );
    Assert.assertNotNull( client.getCreatedAt() );
    Assert.assertNotNull( client.getUpdatedAt() );
  }

}

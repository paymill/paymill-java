package com.paymill.services;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.exception.PaymillException;
import com.paymill.models.Client;
import com.paymill.models.PaymillList;

public class ClientServiceTest {

  private String        email       = "john.rambo@qaiware.com";
  private String        description = "Boom, boom, shake the room";

  private ClientService clientService;

  private List<Client>  clients     = new ArrayList<Client>();

  private Client        clientWithDescriptionAndEmail;

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( "255de920504bd07dad2a0bf57822ee40" );
    this.clientService = paymill.getClientService();
  }

  @AfterClass
  public void tearDown() {
    List<Client> clients = this.clientService.list().getData();
    for( Client client : clients ) {
      Assert.assertNull( this.clientService.delete( client ).getId() );
    }
    Assert.assertEquals( this.clientService.list().getData().size(), 0 );
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
    Client client = this.clientService.get( this.clientWithDescriptionAndEmail );
    this.validateClient( client );
    Assert.assertEquals( client.getDescription(), this.description );
  }

  @Test( dependsOnMethods = "testCreate_WithDescription_shouldSecceed" )
  public void testUpdate_shouldSecceed() {
    this.clientWithDescriptionAndEmail.setEmail( "john.firstblood.rambo@qaiware.com" );
    this.clientWithDescriptionAndEmail.setDescription( "Boom, boom, update the room" );
    this.clientWithDescriptionAndEmail = this.clientService.update( this.clientWithDescriptionAndEmail );

    this.validateClient( this.clientWithDescriptionAndEmail );
    Assert.assertEquals( this.clientWithDescriptionAndEmail.getEmail(), "john.firstblood.rambo@qaiware.com" );
    Assert.assertEquals( this.clientWithDescriptionAndEmail.getDescription(), "Boom, boom, update the room" );
    Assert.assertNull( this.clientWithDescriptionAndEmail.getAppId() );
  }

  @Test( dependsOnMethods = "testUpdate_shouldSecceed" )
  public void testListOrderByEmailAsc() {
    Client.Order order = Client.createOrder().byEmail().asc();

    PaymillList<Client> wrapper = this.clientService.list( null, order );
    List<Client> clients = wrapper.getData();

    Assert.assertNotNull( clients );
    Assert.assertFalse( clients.isEmpty() );
    Assert.assertEquals( clients.size(), this.clients.size() );
    Assert.assertNull( clients.get( 0 ).getEmail() );
    Assert.assertEquals( clients.get( 1 ).getEmail(), "john.firstblood.rambo@qaiware.com" );
  }

  @Test( dependsOnMethods = "testListOrderByEmailAsc" )
  public void testListFilterByEmail() {
    Client.Filter filter = Client.createFilter().byEmail( "john.rambo@qaiware.com" );

    PaymillList<Client> wrapper = this.clientService.list( filter, null );
    List<Client> clients = wrapper.getData();

    Assert.assertNotNull( clients );
    Assert.assertFalse( clients.isEmpty() );
    Assert.assertEquals( this.clients.size() - 2, clients.size() );

    Assert.assertEquals( clients.get( 0 ).getEmail(), "john.rambo@qaiware.com" );
    Assert.assertEquals( clients.get( 1 ).getEmail(), "john.rambo@qaiware.com" );
  }

  private void validateClient( final Client client ) {
    Assert.assertNotNull( client );
    Assert.assertNotNull( client.getId() );
    Assert.assertNotNull( client.getCreatedAt() );
    Assert.assertNotNull( client.getUpdatedAt() );
  }

}

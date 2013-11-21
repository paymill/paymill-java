package com.paymill.services;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paymill.context.PaymillContext;
import com.paymill.models.Webhook;
import com.paymill.models.Webhook.EventType;

public class WebhookServiceTest {

  private WebhookService webhookService;

  private List<Webhook>  webhooks   = new ArrayList<Webhook>();
  private Webhook        webhook;

  private EventType[]    eventTypes = new EventType[] { EventType.CLIENT_UPDATED, EventType.TRANSACTION_SUCCEEDED };
  private String         email      = "john.rambo@qaiware.com";
  private String         url        = "http://www.example.com";

  @BeforeClass
  public void setUp() {
    PaymillContext paymill = new PaymillContext( System.getProperty( "apiKey" ) );
    this.webhookService = paymill.getWebhookService();
  }

  @AfterClass
  public void tearDown() {
    List<Webhook> webhooks = this.webhookService.list().getData();
    for( Webhook webhook : webhooks ) {
      Assert.assertNull( this.webhookService.delete( webhook ).getId() );
    }
    Assert.assertEquals( this.webhookService.list().getData().size(), 0 );
  }

  @Test
  public void testCreateEmailWebhook_shouldSecceed() {
    Webhook webhook = this.webhookService.createEmailWebhook( this.email, this.eventTypes );
    Assert.assertNotNull( webhook );
    this.validateWebhook( webhook );
    Assert.assertNull( webhook.getUrl() );
    Assert.assertNotNull( webhook.getEmail() );
    Assert.assertEquals( webhook.getEmail(), this.email );
    Assert.assertNotNull( webhook.getEventTypes() );
    Assert.assertEquals( webhook.getEventTypes().length, 2 );
    this.webhooks.add( webhook );
  }

  @Test
  public void testCreateUrlWebhook_shouldSecceed() {
    Webhook webhook = this.webhookService.createUrlWebhook( this.url, this.eventTypes );
    Assert.assertNotNull( webhook );
    this.validateWebhook( webhook );
    Assert.assertNotNull( webhook.getUrl() );
    Assert.assertEquals( webhook.getUrl(), this.url );
    Assert.assertNull( webhook.getEmail() );
    Assert.assertNotNull( webhook.getEventTypes() );
    Assert.assertEquals( webhook.getEventTypes().length, 2 );
    this.webhook = webhook;
    this.webhooks.add( webhook );
  }

  @Test( dependsOnMethods = "testCreateUrlWebhook_shouldSecceed" )
  public void testShow_shouldSecceed() {
    Webhook webhook = this.webhookService.get( this.webhook );
    this.validateWebhook( webhook );
    Assert.assertNotNull( webhook.getUrl() );
    Assert.assertEquals( webhook.getUrl(), this.url );
    Assert.assertNull( webhook.getEmail() );
    Assert.assertNotNull( webhook.getEventTypes() );
    Assert.assertEquals( webhook.getEventTypes().length, 2 );
  }

  @Test( dependsOnMethods = "testShow_shouldSecceed" )
  public void testUpdate_shouldSecceed() throws Exception {
    this.webhook.setUrl( "http://www.example.org" );
    this.webhookService.update( this.webhook );

    this.validateWebhook( this.webhook );
    Assert.assertNotNull( this.webhook.getUrl() );
    Assert.assertEquals( this.webhook.getUrl(), "http://www.example.org" );
    Assert.assertNull( this.webhook.getEmail() );
    Assert.assertNotNull( this.webhook.getEventTypes() );
  }

  private void validateWebhook( final Webhook webhook ) {
    Assert.assertNotNull( webhook );
    Assert.assertNotNull( webhook.getId() );
    Assert.assertNotNull( webhook.getCreatedAt() );
    Assert.assertNotNull( webhook.getUpdatedAt() );
  }

}

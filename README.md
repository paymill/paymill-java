![PAYMILL icon](https://static.paymill.com/r/335f99eb3914d517bf392beb1adaf7cccef786b6/img/logo-download_Light.png)
# paymill-java

Java wrapper for PAYMILL API

[![Build Status](https://travis-ci.org/paymill/paymill-java.png?branch=master)](https://travis-ci.org/paymill/paymill-java)

## Getting started

- If you are not familiar with PAYMILL, start with the [documentation](https://developers.paymill.com).
- Install the latest release.
- Check the API [reference](https://developers.paymill.com/API/).
- Check the full JavaDoc [documentation](http://paymill.github.io/paymill-java/).
- Check the tests.


## Installation

- Releases are available in [maven central](http://search.maven.org/#artifactdetails|com.paymill|paymill-java|5.1.6|jar) and in this [repository](https://github.com/paymill/paymill-java/releases/tag/v5.1.3).

```xml
<dependency>
  <groupId>com.paymill</groupId>
  <artifactId>paymill-java</artifactId>
  <version>5.1.6</version>
</dependency>
```


## What's new

We have released version 5, which follows version 2.1 of the PAYMILL's REST API. This version is not backwards compatible with version 4, altough changes are minor.
We also added some [examples](/samples/) , how to use an alternative http client and how to  deal with incoming webhooks.

## Usage

Initialize the library by providing your api key:
```java
  PaymillContext paymillContext = new PaymillContext( "<YOUR PRIVATE API KEY>" );
```
PaymillContecxt loads the context of PAYMILL for a single account, by providing a merchants private key. It creates 8 services, which represents the PAYMILL API:
 * ChecksumService
 * ClientService
 * OfferService
 * PaymentService
 * PreauthorizationService
 * RefundService
 * SubscriptionService
 * TransactionService
 * WebhookService

These services should not be created directly. They have to be obtained by the context's accessors.

To run the tests:
```
  mvn -DapiKey=<YOUR_PRIVATE_API_KEY> test
```

### Using services


In all cases, you'll use the predefined service classes to access the PAYMILL API.

To fetch a service instance, call *service name* accessor from paymillContext, like
```java
  ClientService clientService = paymillContext.getClientService();
```
Every service instance provides basic methods for CRUD functionality.

### Creating objects

Every service provides instance factory methods for creation. They are very different for every service, because every object can be created in a different way. The common pattern is
```java
  xxxService.createXXX( params... );
```
For example: client can be created with two optional parameters: *email* and *description*. So we have four possible methods to create the client:
* clientService.create() - creates a client without email and description
* clientService.createWithEmail( "john.rambo@paymill.com" ) - creates a client with email
* clientService.createWithDescription( "CRM Id: fake_34212" ) - creates a client with description
* clientService.createWithEmailAndDescription( "john.rambo@paymill.com", "CRM Id: fake_34212" ) - creates a client with email and description

### Retrieving objects

You can retrieve an object by using the get() method with an object id:
```java
  Client client = clientService.get( "client_12345" );
```
or with the instance itself, which also refreshes it:
```java
  clientService.get( client );
```
This method throws an ApiException if there is no client under the given id.

*Important*: If you use a nested object (e.g. ` paymet = transaction.getClient().getPayments().get(0) ` ) you should always "refresh", as the nested object will contain only the id, and all other properties will be null.

### Retrieving lists

To retrieve a list you may simply use the list() method:
```java
  PaymillList<Client> clients = clientService.list();
```
You may provide a filter and order to list method:
```java
  PaymillList<Client> clients =
    clientService.list(
      Client.createFilter().byEmail( "john.rambo@paymill.com" ),
      Client.createOrder().byCreatedAt().desc()
    );
```
This will load only clients with email john.rambo@paymill.com, order descending by creation date.

### Updating objects

In order to update an object simply call a service's update() method:
```java
  clientServive.update( client );
```
The update method also refreshes the the given instance. For example: If you changed the value of 'createdAt' locally and  pass the instance to the update() method, it will be refreshed with the data from PAYMILL. Because 'createdAt' is not updateable field your change will be lost.

### Deleting objects

You may delete objects by calling the service's delete() method with an object instance or object id.
```java
  clientService.delete( "client_12345" );
```
or
```java
  clientService.delete( client );
```

### Using an alternative http client

Since version 5.0.0 the wrapper supports alternative http clients. To use one, you need to take these two steps:

1. Exclude the jersey dependecy from your pom like this:
```xml
  		<dependency>
			<groupId>com.paymill</groupId>
			<artifactId>paymill-java</artifactId>
			<version>latestversion</version>
			<exclusions>
				<exclusion>
					<groupId>org.glassfish.jersey.core</groupId>
					<artifactId>jersey-client</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
```
2. Implement the HttpClient interface and create a PaymillContext with it.

We have an [example](/samples/jerseyOneHttp) with Jersey 1.X, the client used prior the 5.X release of the wrapper.

## Spring integration

This example is suitable if you use this wrapper for a single account.

Defines the PAYMILL context in Spring context.

```xml
<bean id="paymillContext" class="com.paymill.context.PaymillContext">
  <constructor-arg value="<YOUR PRIVATE API KEY>" />
</bean>
```

Defines custom Controller, which uses PAYMILL ClientService internaly. Note that the setter receives *paymillContext*.
```xml
<bean id="clientController" class="com.yourpackage.ClientController">
  <property name="clientService" ref="paymillContext" />
</bean>
```

The ClientController class itself. Note that the clientService property is set by getting the ClientService form the paymillContext.

```java
public class ClientController {
  private ClientService clientService;

  public void setClientService( PaymillContext paymillContext ) {
    this.clientService = paymillContext.getClientService();
  }
}
```
## Scala and Groovy integration

The wrapper can be used easily in Scala and Groovy projects. Note, that it depends on 3rd party libraries, thus usage with a dependecy managment tool like maven is recommended.

### Scala example:

```scala
import com.paymill.context.PaymillContext

object HelloPaymill {
  def main(args: Array[String]) {
    val context = new PaymillContext("<YOUR PRIVATE API KEY>")
    val client = context.getClientService().createWithEmail("lovely-client@example.com")
    println(client.getId())
  }
}
```

### Groovy example:

```groovy
import com.paymill.context.PaymillContext

class HelloPaymill {

    public static void main(String[] args) {
        def context = new PaymillContext("<YOUR PRIVATE API KEY>")
        def client = context.getClientService().createWithEmail("lovely-client@example.com")
        println(client.getId())
    }
}

```

## Dealing with webhooks

Take a look at the Webhook [sample](/samples/webhookresolver). It contains an example json deserializer, as well as hints, how to receive webhooks in your app.

## Older API versions

The wrapper supports only the latest version of the PAYMILL Rest API (v2.1). Latest stable releases for older API versions:

* API v2.0 : 3.2.0 in [maven central](http://search.maven.org/#artifactdetails|com.paymill|paymill-java|3.2.0|jar) and in this [repository](https://github.com/paymill/paymill-java/releases/tag/v3.2.0).

## Changelog

### 5.1.6
* fix: Smaller issues with Javadoc Comments

### 5.1.3
* fix: [#65](https://github.com/paymill/paymill-java/issues/65) pass on parameter 'description' in TransactionService.createWithPaymentAndClient, thanks to [@rethab](https://github.com/rethab)

### 5.1.2
* fix: [#64](https://github.com/paymill/paymill-java/issues/64) Unboxing null always throws NullPointerException, thanks to [@vladaspasic](https://github.com/vladaspasic)

### 5.1.1
* fix: [#64](https://github.com/paymill/paymill-java/issues/64) Unboxing null always throws NullPointerException, thanks to [@vladaspasic](https://github.com/vladaspasic)

### 5.1.0

* fix tests: Token is now generated for each request
* update: projcet dependencies
* add SEPA mandate_reference for direct debit transaction and subscription
* include internal objects [ShoppingCartItem](https://github.com/paymill/paymill-java/blob/master/src/main/java/com/paymill/models/ShoppingCartItem.java) and [Address](https://github.com/paymill/paymill-java/blob/master/src/main/java/com/paymill/models/Address.java)
* add ChecksumService to create checksums for transactions that are started client-side, e.g. PayPal checkout.

### 5.0.0

* Minor, but incompatible changes in the interface
* fix: [#57](https://github.com/paymill/paymill-java/issues/57) removed final keywords from some classes to allow proper mocking, thanks to [@dobermai](https://github.com/dobermai)
* fix: [#59](https://github.com/paymill/paymill-java/issues/59) added missing subscription status, thanks to @schaebo
* improved deserialization and fixed test suite to work with arbitary accounts
* improvement: [#51](https://github.com/paymill/paymill-java/issues/51) isolated jersey dependecy, alternative frameworks can now be used for http, thanks to @basoko
* fix: [#50](https://github.com/paymill/paymill-java/issues/50) fixed interval deserialization, thanks to @basoko
* improvement: [#53](https://github.com/paymill/paymill-java/issues/53) added missing payment object fields
* switched to jersey 2, thanks to Dmitry.
* updated webhook event types
* included internal objects
* update project dependencies

### 4.0.1
* improvement: [#54](https://github.com/paymill/paymill-java/issues/54) now is possible to end the trial

### 4.0.0
* Works with version 2.1 of PAYMILL's REST API.
* update project dependencies

### 3.2.0
* improvement: remove workaround for subscriptions without offer.
* improvement: [#23](https://github.com/paymill/paymill-java/issues/23) now preauthorizations can be created with description.
* fix: after update of the offer the PAYMILL API does not returns 0 days trial period any more.
* update project dependencies

### 3.1.2
* fix: [#47](https://github.com/paymill/paymill-java/issues/47) TransacionService list with filter for createdAt doesn't work.
* fix: typo in Webhook.EventType.TRANSACTION_FAILED
* improvement: missing enumeration value will not cause parsing error, but will be mapped to UNDEFINED
* update project dependencies

### 3.1.1
* fix in *createWithOfferPaymentAndClient*: now *trialStart* represents the timestamp in correct format.

### 3.1.0
* Allow update of an offer for a subscription
* update project dependencies

### 3.0.5
* internal improvements
* [#42](https://github.com/paymill/paymill-java/issues/42) add currency to fee
* update project dependencies

### 3.0.4
* [#38](https://github.com/paymill/paymill-java/issues/38) explicit dependency to *jersey-core*

### 3.0.3
* update project dependencies
* Ability to set HTTP connection timeout in milliseconds to PaymillContext constructor (infinity by default)
* [#39](https://github.com/paymill/paymill-java/issues/39) fix deserialization of Subscription nextCaptureAt

### 3.0.2
* fix: remove **_id** in names for reference types in filter parameters for list methods( eg. *client_id* to *client* )

### 3.0.1
* Add "chargeback" value to Transaction.Status enum
* Add workaround for subscription without offer. Now the lib will not throw parsing error.

### 3.0.0
* Improved implementation internally, now most of the configuration uses annotations and reflection.
* Package name changed from de.paymill to com.paymill.
- Removed singleton Paymill object and replaced with a PaymillContext object.
+ All services are now directly accessible from PaymillContext.
+ Improved list handling. Now the list method returns a PaymillList, which also contains the dataCount. All models now contain methods to create filter and sort criteria.
+ Improved create methods. All create methods now work with arguments. Removed unnecessary fields in objects (e.g. token in Transaction).
+ Improved get, update and remove methods. Get and remove now work with both instances and IDs. Get and Update methods now work on the same instance.
- Offer is no longer updateable for a subscription.

### 2.6
First maven central

## License

Copyright 2013 PAYMILL GmbH.

MIT License (enclosed)

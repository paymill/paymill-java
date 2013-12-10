![PAYMILL icon](https://static.paymill.com/r/335f99eb3914d517bf392beb1adaf7cccef786b6/img/logo-download_Light.png)
# paymill-java

Java wrapper for PAYMILL API

[![Build Status](https://travis-ci.org/paymill/paymill-java.png?branch=master)](https://travis-ci.org/paymill/paymill-java) [![Dependency Status](https://www.versioneye.com/user/projects/51f106fa632bac3e2b036ded/badge.png)](https://www.versioneye.com/user/projects/51f106fa632bac3e2b036ded)

## Getting started

- If you are not familiar with PAYMILL, start with the [documentation](https://www.paymill.com/en-gb/documentation-3/).
- Install the latest release.
- Check the API [reference](https://www.paymill.com/en-gb/documentation-3/reference/api-reference/).
- Check the full JavaDoc [documentation](http://paymill.github.io/paymill-java/).
- Check the tests.


## Installation

- Releases are available in [maven central](http://search.maven.org/#artifactdetails|com.paymill|paymill-java|3.0.2|jar) and in this [repository](https://github.com/paymill/paymill-java/releases/tag/v3.0.2) .

```
<dependency>
  <groupId>com.paymill</groupId>
  <artifactId>paymill-java</artifactId>
  <version>3.0.2</version>
</dependency>
```


## What's new

We have released version 3. This version is not backwards compatible with version 2. Concrete changes in the changelog.

## Usage

Initialize the library by providing your api key:
```
  PaymillContext paymillContext = new PaymillContext( "<YOUR PRIVATE API KEY>" );
```
PaymillContecxt loads the context of PAYMILL for a single account, by providing a merchants private key. It creates 8 services, which represents the PAYMILL API:
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
```
  ClientService clientService = paymillContext.getClientService();
```
Every service instance provides basic methods for CRUD functionality.

### Creating objects

Every service provides instance factory methods for creation. They are very different for every service, because every object can be created in a different way. The common pattern is
```
  service.createXXX( params... );
```
For example: client can be created with two optional parameters: *email* and *description*. So we have four possible methods to create the client:
* clientService.create() - creates a client without email and description
* clientService.createWithEmail( "john.rambo@paymill.com" ) - creates a client with email
* clientService.createWithDescription( "CRM Id: fake_34212" ) - creates a client with description
* clientService.createWithEmailAndDescription( "john.rambo@paymill.com", "CRM Id: fake_34212" ) - creates a client with email and description

### Retrieving objects

You can retrieve an object by using the get() method with an object id:
```
  Client client = clientService.get( "client_12345" );
```
or with the instance itself, which also refreshes it:
```
  clientService.get( client );
```
This method throws an ApiException if there is no client under the given id.

### Retrieving lists

To retrieve a list you may simply use the list() method:
```
  PaymillList<Client> clients = clientService.list();
```
You may provide a filter and order to list method:
```
  PaymillList<Client> cliens =
    clientService.list(
      Client.createFilter().byEmail( "john.rambo@paymill.com" ),
      Client.createOrder().byCreatedAt().desc()
    );
```
This will load only clients with email john.rambo@paymill.com, order descending by creation date.

### Updating objects

In order to update an object simply call a service's update() method:
```
  clientServive.update( client );
```
The update method also refreshes the the given instance. For example: If you changed the value of 'createdAt' locally and  pass the instance to the update() method, it will be refreshed with the data from PAYMILL. Because 'createdAt' is not updateable field your change will be lost.

### Deleting objects

You may delete objects by calling the service's delete() method with an object instance or object id.
```
  clients.delete( "client_12345" );
```
or
```
  clients.delete( client );
```
## Spring integration

This example is suitable if you use this wrapper for a single account.

Defines the PAYMILL context in Spring context.

```
<bean id="paymillContext" class="com.paymill.context.PaymillContext">
  <constructor-arg value="<YOUR PRIVATE API KEY>" />
</bean>
```

Defines custom Controller, which uses PAYMILL ClientService internaly. Note that the setter receives *paymillContext*.
```
<bean id="clientController" class="com.yourpackage.ClientController">
  <property name="clientService" ref="paymillContext" />
</bean>
```

The ClientController class itself. Note that the clientService property is set by getting the ClientService form the paymillContext.

```
public class ClientController {
  private ClientService clientService;

  public void setClientService( PaymillContext paymillContext ) {
    this.clientService = paymillContext.getClientService();
  }
}
```

## Changelog

### 3.0.2
fix: remove _id in names for reference types in filter parameters for list methods( eg. *client_id* to *client* )

### 3.0.1
* Add "chargeback" value to Transaction.Status enum
* Add workaround for subscription without offer. Now the lib will not throw parsing error.

### 3.0.0
* Improved implementation internally, now most of the configuration uses annotations and reflection.
* Package name changed from de.paymill to com.paymill .
- Removed singleton Paymill object and replaced with a PaymillContext object.
+ All services are now directly accessible from PaymillContext.
+ Improved list handling. Now the list method returns a PaymillList, which also contains the dataCount. All models now contain methods to create list filters and an order.
+ Improved create methods. All create methods now work with arguments. Removed unnecessary fields in objects (e.g. token in Transaction).
+ Improved get, update and remove methods. Get and remove now work with both instances and IDs. Get and Update methods now work on the same instance.
- Offer is no longer updateable for a subscription.

### 2.6
First maven central

## License

Copyright 2013 PAYMILL GmbH.

MIT License (enclosed)


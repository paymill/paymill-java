paymill-java
============
Java wrapper for PAYMILL API

[![Build Status](https://travis-ci.org/paymill/paymill-java.png?branch=master)](https://travis-ci.org/paymill/paymill-java) [![Dependency Status](https://www.versioneye.com/user/projects/51f106fa632bac3e2b036ded/badge.png)](https://www.versioneye.com/user/projects/51f106fa632bac3e2b036ded)

Initialize the library by providing your api key:

  PaymillContext paymillContext = new PaymillContext( "<YOUR PRIVATE API KEY>" );

PaymillContecxt loads the context of PAYMILL for a single account, by providing a merchants private key. It creates 8 services, which represents the PAYMILL API:
 * ClientService
 * OfferService
 * PaymentService
 * PreauthorizationService
 * RefundService
 * SubscriptionService
 * TransactionService
 * WebhookService

This services should not be created directly. They have to be obtained by the context's accessors.

To run the tests:

        mvn -DapiKey=<YOUR_PRIVATE_API_KEY> test


Using services
--------------

In all cases, you'll use the predefined service classes to access the paymill api.

To fetch a service instance, call *service name* accessor from paymillContext, like

  ClientService clients = paymillContext.getClientService();

Every service instance provides basic methods for CRUD functionality.

### Creating objects

Every service provides an instance factory methods for creation. They are very different for every service, because every object can be created in a different way. The common pattern is

  service.createXXX( params... );

For example client can be created with two optional parameters: *email* and *description*. So we have four possible methods to create the client:
* clientService.create() - creates a client without email and description
* clientService.createWithEmail( "john.rambo@paymill.com" ) - creates a client with email
* clientService.createWithDescription( "CRM Id: fake_34212" ) - creates a client with description
* clientService.createWithEmailAndDescription( "john.rambo@paymill.com", "CRM Id: fake_34212" ) - creates a client with email and description

### Retrieving objects

You retrieve an object by using the get() method with an object id:

  Client client = clientsService.get( "client_12345" );

or with the insnance itself, which also refreshs it:

  clientService.get( client );

This method throws an ApiException if there is no client under the given id.

### Retrieving lists

To retrieve a list you may simply use the list() method:

  PaymillList<Client> clients = clientService.list();

You may provide a filter and order to list method:

  PaymillList<Client> cliens =
    clientService.list(
      Client.createFilter().byEmail( "john.rambo@paymill.com" ),
      Client.createOrder().byCreatedAt().desc()
    );

This will load only clients with email john.rambo@paymill.com, order descending by creation date.

### Updating objects

In order to update an object simply call a service's update() method:

  clientServive.update( client );

The update method also make a refresh of the given instance. For example if you have a local change for createdAt value. When passing the object to update() method the instance will be refreshed with the data from PAYMILL and because createdAt is not updateable field your change will be lost.

### Deleting objects

You may delete objects by calling the service's delete() method with an object instance or object id.

  clients.delete( "client_12345" );

or

  clients.delete( client );

Further info
------------

See our api reference for more examples: [https://www.paymill.com/en-gb/documentation-3/reference/api-reference/index.html]()


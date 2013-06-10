paymill-java
============
Java wrapper for Paymill API

[![Build Status](https://travis-ci.org/paymill/paymill-java.png?branch=master)](https://travis-ci.org/paymill/paymill-java)

Initialize the library by setting your api key:

	Paymill.setApiKey("<YOUR API KEY>");

You may provide the api key and api url through command line args by using the
-D arg of the jvm.

	java -DapiKey=<YOUR_API_KEY> -DapiUrl=<API_URL>

To run the tests:

        mvn -DapiKey=<YOUR_API_KEY> test


The api url defaults to https://api.paymill.com/v2

Using services
--------------

In most cases, you'll use the predefined service classes to access the paymill api.

To fetch a service instance, call Paymill.getService(), like

	ClientService clients = Paymill.getService(ClientService.class);

Every service instance provides basic methods for CRUD functionality.

### Retrieving objects

You retrieve an object by using the get() method with an object id.

	Client client = clients.get("client_12345");

This method throws an ApiException if there is no client under the given id.

### Retrieving lists

To retrieve a list you may simply use the list() method:

	List<Client> clientList = clients.list();

This will, however, be rather inefficient because it loads all clients without a limit or filter.

You may provide a limit to only retrieve a defined amount of objects:

	List<Client> clientList = clients.list(10, 5);

This will load 5 clients, starting at the tenth client.

You can also filter a list by providing filter criteria:

	Filter filter = new Filter();
	filter.add("amount", ">100");
	List<Transaction> txList = transactions.list(filter);

This will return a list of all transactions having an amount of more than 1 Euros.

You may also combine the two calls:

	Filter filter = new Filter();
	filter.add("amount", ">100");
	List<Transaction> txList = transactions.list(10, 5, filter);

### Updating objects

In order to update an object simply call a service's update() method:

	Client client = clients.get("client_12345");
	// do stuff
	clients.update(client);

You may also update an object without loading it first:

	Client client = new Client();
	client.setId("client_12345");
	client.setEmail("new-mail@example.com");
	clients.update(client);

### Deleting objects

You may delete objects by calling the service's delete() method with an object instance or object id.

	clients.delete("client_12345");

Further info
------------

See our api reference for more examples: [https://www.paymill.com/en-gb/documentation-3/reference/api-reference/index.html]()


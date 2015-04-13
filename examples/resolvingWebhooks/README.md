![PAYMILL icon](https://static.paymill.com/r/335f99eb3914d517bf392beb1adaf7cccef786b6/img/logo-download_Light.png)
# paymill-java
## Example of receiving a webhook 


Webhooks are a nice feature, that lets you application receive asynchronous updates from PAYMILL, when something happens with your account. To work with webhooks, you first need to create one. Go to the merchant center and create a webhook, choosing the events you want to receive and entering the URL, where you application expects to receive the webhooks.

First copy the WebhookResolver from this project into yours. Now, in your application's endpoint(s), you need to extract the body from the PAYMILL request. Note, that you can have an individual endpoint for each event by creating a webhook for each one or you can have only one endpoint and react based on the event type. With Spring MVC, you would want to use something similar to:


```java
@RequestMapping(value = "/paymill" method = RequestMethod.POST)
@ResponseStatus(value = HttpStatus.OK)
public void receivePaymillWebhook( @RequestBody String body ) {
   ...
}   
```


The response should only contain the HTTP Status OK. Don't send anything in addition.

Now, when you have the JSON String, you can use it convert it to a WebhookResolver using the static "fromJson" method. The returned object contains the Webhook type and the Object(s). 

```java
@RequestMapping(value = "/paymill" method = RequestMethod.POST)
@ResponseStatus(value = HttpStatus.OK)
public void receivePaymillWebhook( @RequestBody String body ) {
   WebhookResolver resolver = WebhookResolver.fromString( body );
   switch( resolver.getEventType() ) {
      case TRANSACTION_CREATED:
        Transaction transaction = paymillContext.getTransactionService().get( resolver.getTransaction() );
        // do something with the transaction here
        break;
      ....
      default:
        break;
    }
}   
```

You may notice the "refreshing" of the transaction object from the resolver. It is important  for security reasons. Since the webhook endpoint is public, anyone (and not just PAYMILL) could have made the above request. By calling the PAYMILL API again, we make sure that the transaction object contains the real data.
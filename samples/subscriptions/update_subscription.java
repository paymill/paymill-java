SubscriptionService subscriptionService = paymillContext.getSubscriptionService();
Subscription subscription = subscriptionService.get( "sub_dea86e5c65b2087202e3" );
subscription.setName( "Changed Subscription" );
subscriptionService.update( subscription );

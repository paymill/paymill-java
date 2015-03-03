Offer offer = paymillContext.getOfferService().get( "offer_d7e9813a25e89c5b78bd" );
SubscriptionService subscriptionService = paymillContext.getSubscriptionService();
Subscription subscription = subscriptionService.get( "sub_dea86e5c65b2087202e3" );
subscriptionService.changeOfferChangeCaptureDateAndRefund( subscription, offer );

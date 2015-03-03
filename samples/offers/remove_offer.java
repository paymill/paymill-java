OfferService offerService = paymillContext.getOfferService();
boolean removeWithSubscriptions = true;
offerService.delete( "offer_40237e20a7d5a231d99b", removeWithSubscriptions );

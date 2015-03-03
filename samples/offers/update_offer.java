OfferService offerService = paymillContext.getOfferService();
Offer offer = offerService.get( "offer_40237e20a7d5a231d99b" );

offer.setName("Extended Special");
offer.setInterval("1 MONTH")
offer.setAmount(3333);
offer.setCurrency("USD");
offer.setTrialPeriodDays(33);

boolean updateSubscriptions=true;
offerService.update( offer,updateSubscriptions );

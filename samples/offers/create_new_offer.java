OfferService offerService = paymillContext.getOfferService();
Offer offer = offerService.create(
    4200,
    "EUR",
    "1 WEEK",
    "Nerd Special",
    0
);

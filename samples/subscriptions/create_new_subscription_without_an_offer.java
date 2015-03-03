SubscriptionService subscriptionService = paymillContext.getSubscriptionService();
subscriptionService.create( Subscription.create(
    "pay_5e078197cde8a39e4908f8aa",
    3000,
    "EUR",
    Interval.periodWithChargeDay( 1, Unit.WEEK, Weekday.MONDAY )
    )
    .withName( "Example Subscription" )
    .withPeriodOfValidity( Interval.period( 2, Unit.YEAR ) )
    .withStartDate( new Date( 1400575533 ) )
);

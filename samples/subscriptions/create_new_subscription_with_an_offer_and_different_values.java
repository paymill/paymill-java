SubscriptionService subscriptionService = paymillContext.getSubscriptionService();
subscriptionService.create( Subscription.create(
    "pay_95ba26ba2c613ebb0ca8",
    "offer_40237e20a7d5a231d99b"
).withAmount( 3000 )
.withCurrency( "EUR" )
.withInterval( Interval.periodWithChargeDay( 1, Unit.WEEK, Weekday.MONDAY ) )
.withPeriodOfValidity( Interval.period( 2, Unit.YEAR ) )
.withStartDate( new Date( 1400575533 ) )
);

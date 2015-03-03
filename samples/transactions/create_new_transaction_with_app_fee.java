Fee fee = new Fee();
fee.setAmount( 420 );
fee.setPayment( "pay_3af44644dd6d25c820a8" );
TransactionService transactionService = paymillContext.getTransactionService();
Transaction transaction = transactionService.createWithTokenAndFee(
    "098f6bcd4621d373cade4e832627b4f6",
    4200,
    "EUR",
    fee
);

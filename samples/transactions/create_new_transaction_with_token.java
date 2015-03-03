TransactionService transactionService = paymillContext.getTransactionService();
Transaction transaction = transactionService.createWithToken(
    "098f6bcd4621d373cade4e832627b4f6",
    4200,
    "EUR",
    "Test Transaction"
);

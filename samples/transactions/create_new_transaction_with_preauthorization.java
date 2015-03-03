PreauthorizationService preauthorizationService = paymillContext.getPreauthorizationService();
Preauthorization preauthorization = preauthorizationService.createWithToken(
    "098f6bcd4621d373cade4e832627b4f6",
    4200,
    "EUR"
).getPreauthorization();

TransactionService transactionService = paymillContext.getTransactionService();
Transaction transaction = this.transactionService.createWithPreauthorization(
    preauthorization,
    4200,
    "EUR",
    "Test Transaction"
);

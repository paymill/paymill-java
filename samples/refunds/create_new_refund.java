TransactionService = paymillContext.getTransactionService();
Transaction transaction = this.transactionService.createWithToken(
    "098f6bcd4621d373cade4e832627b4f6",
    4200,
    "EUR",
    "For refund"
);
RefundService = paymillContext.getRefundService();
Refund refund = refundService.refundTransaction(
    transaction,
    4200,
    "Sample Description"
);

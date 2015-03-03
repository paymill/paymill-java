TransactionService transactionService = paymillContext.getTransactionService();
Transaction transaction = transactionService.get("tran_023d3b5769321c649435");
transaction.setDescription("My updated transaction description");
transactionService.update( transaction );

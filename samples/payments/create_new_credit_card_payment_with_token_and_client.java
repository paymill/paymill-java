PaymentService paymentService = paymillContext.getPaymentService();
Payment payment = paymentService.createWithTokenAndClient(
    "098f6bcd4621d373cade4e832627b4f6",
    "client_88a388d9dd48f86c3136"
);

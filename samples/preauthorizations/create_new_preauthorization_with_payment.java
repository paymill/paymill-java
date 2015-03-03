PaymentService paymentService = paymillContext.getPaymentService();
Payment payment = paymentService.createWithToken(
    "098f6bcd4621d373cade4e832627b4f6"
);
PreauthorizationService preauthorizationService = paymillContext.getPreauthorizationService();
Preauthorization preauthorization = preauthorizationService.createWithPayment(
    payment,
    4200,
    "EUR",
    "example description"
);

ClientService clientService = paymillContext.getClientService();
Client client = clientService.createWithEmailAndDescription(
    "lovely-client@example.com",
    "Lovely Client"
);

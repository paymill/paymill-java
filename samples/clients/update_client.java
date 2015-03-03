ClientService clientService = paymillContext.getClientService();
Client client = clientService.get("client_88a388d9dd48f86c3136");
client.setDescription("My Lovely Client");
clientService.update( client );

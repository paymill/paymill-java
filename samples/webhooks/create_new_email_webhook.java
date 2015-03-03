WebhookService webhookService = paymillContext.getWebhookService();
EventType[] eventTypes = new EventType[] {
    EventType.TRANSACTION_SUCCEEDED,
    EventType.TRANSACTION_FAILED
};
Webhook webhook = webhookService.createEmailWebhook(
    "<your-webhook-email>",
    eventTypes
);

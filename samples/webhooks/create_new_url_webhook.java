WebhookService webhookService = paymillContext.getWebhookService();
EventType[] eventTypes = new EventType[] {
    EventType.TRANSACTION_SUCCEEDED,
    EventType.TRANSACTION_FAILED
};
Webhook webhook = webhookService.createUrlWebhook(
    "<your-webhook-url>",
    eventTypes
);

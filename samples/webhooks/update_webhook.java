WebhookService webhookService = paymillContext.getWebhookService();
Webhook webhook = webhookService.get("hook_40237e20a7d5a231d99b");
webhook.setUrl("http://www.example.org");
webhookService.update( webhook );

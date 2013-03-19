{
	"event" : {
		"event_type" : "subscription.failed",
		"event_resource" : {
			"subscription" : {
				"id" : "sub_f3f78037889f4d74f0fe",
				"offer" : [],
				"livemode" : true,
				"cancel_at_period_end" : false,
				"trial_start" : null,
				"trial_end" : null,
				"next_capture_at" : 1362265200,
				"created_at" : 1359642763,
				"updated_at" : 1359642765,
				"canceled_at" : null,
				"payment" : {
					"id" : "pay_91b81cba9aab53781df404755736",
					"type" : "creditcard",
					"client" : "client_99cc4d4e535f0cda3a7b",
					"card_type" : "visa",
					"country" : null,
					"expire_month" : 3,
					"expire_year" : 2013,
					"card_holder" : "daniel.florey@gmail.com",
					"last4" : "1111",
					"created_at" : 1359642762,
					"updated_at" : 1359642762
				},
				"client" : {
					"id" : "client_99cc4d4e535f0cda3a7b",
					"email" : "daniel.florey@gmail.com",
					"description" : "{\"template\":\"floreysoft\",\"name\":\"Daniel Florey\",\"company\":\"floreysoft GmbH\",\"addressLine1\":\"Telemannstra\u00dfe 22\",\"zip\":\"20255\",\"city\":\"Hamburg\",\"state\":\"HH\",\"country\":\"DE\"}",
					"created_at" : 1359633591,
					"updated_at" : 1359633591,
					"payment" : [ {
						"id" : "pay_0ba78dff3ac9ab83d02bd21d67d9",
						"type" : "creditcard",
						"client" : "client_99cc4d4e535f0cda3a7b",
						"card_type" : "visa",
						"country" : null,
						"expire_month" : 3,
						"expire_year" : 2013,
						"card_holder" : "daniel.florey@gmail.com",
						"last4" : "1111",
						"created_at" : 1359633592,
						"updated_at" : 1359633592
					}, {
						"id" : "pay_c319657866d52d3c65c86c9e5b9d",
						"type" : "creditcard",
						"client" : "client_99cc4d4e535f0cda3a7b",
						"card_type" : "visa",
						"country" : null,
						"expire_month" : 3,
						"expire_year" : 2013,
						"card_holder" : "daniel.florey@gmail.com",
						"last4" : "1111",
						"created_at" : 1359634408,
						"updated_at" : 1359634409
					}, {
						"id" : "pay_11f54e4eda85dfc603b8c8506b18",
						"type" : "creditcard",
						"client" : "client_99cc4d4e535f0cda3a7b",
						"card_type" : "visa",
						"country" : null,
						"expire_month" : 2,
						"expire_year" : 2013,
						"card_holder" : "daniel.florey@gmail.com",
						"last4" : "1111",
						"created_at" : 1359641014,
						"updated_at" : 1359641014
					}, {
						"id" : "pay_8a48c088904e6f14e0459af80562",
						"type" : "creditcard",
						"client" : "client_99cc4d4e535f0cda3a7b",
						"card_type" : "visa",
						"country" : null,
						"expire_month" : 3,
						"expire_year" : 2013,
						"card_holder" : "daniel.florey@gmail.com",
						"last4" : "6355",
						"created_at" : 1359641168,
						"updated_at" : 1359641169
					}, {
						"id" : "pay_74b64219bcefebba218ac471e896",
						"type" : "creditcard",
						"client" : "client_99cc4d4e535f0cda3a7b",
						"card_type" : "visa",
						"country" : null,
						"expire_month" : 3,
						"expire_year" : 2013,
						"card_holder" : "daniel.florey@gmail.com",
						"last4" : "1111",
						"created_at" : 1359642095,
						"updated_at" : 1359642096
					}, {
						"id" : "pay_91b81cba9aab53781df404755736",
						"type" : "creditcard",
						"client" : "client_99cc4d4e535f0cda3a7b",
						"card_type" : "visa",
						"country" : null,
						"expire_month" : 3,
						"expire_year" : 2013,
						"card_holder" : "daniel.florey@gmail.com",
						"last4" : "1111",
						"created_at" : 1359642762,
						"updated_at" : 1359642762
					} ],
					"subscription" : null
				}
			},
			"transaction" : {
				"id" : "tran_8f3e86d7d5586337a1afd1f1ccf5",
				"amount" : "594",
				"origin_amount" : 594,
				"status" : "failed",
				"description" : "Subscription#sub_f3f78037889f4d74f0fe ultradoxPersonal",
				"livemode" : true,
				"refunds" : null,
				"currency" : "EUR",
				"created_at" : 1359642763,
				"updated_at" : 1359642765,
				"response_code" : 50200,
				"payment" : {
					"id" : "pay_91b81cba9aab53781df404755736",
					"type" : "creditcard",
					"client" : "client_99cc4d4e535f0cda3a7b",
					"card_type" : "visa",
					"country" : null,
					"expire_month" : 3,
					"expire_year" : 2013,
					"card_holder" : "daniel.florey@gmail.com",
					"last4" : "1111",
					"created_at" : 1359642762,
					"updated_at" : 1359642762
				},
				"client" : {
					"id" : "client_99cc4d4e535f0cda3a7b",
					"email" : "daniel.florey@gmail.com",
					"description" : "{\"template\":\"floreysoft\",\"name\":\"Daniel Florey\",\"company\":\"floreysoft GmbH\",\"addressLine1\":\"Telemannstra\u00dfe 22\",\"zip\":\"20255\",\"city\":\"Hamburg\",\"state\":\"HH\",\"country\":\"DE\"}",
					"created_at" : 1359633591,
					"updated_at" : 1359633591,
					"payment" : [ "pay_0ba78dff3ac9ab83d02bd21d67d9",
							"pay_c319657866d52d3c65c86c9e5b9d",
							"pay_11f54e4eda85dfc603b8c8506b18",
							"pay_8a48c088904e6f14e0459af80562",
							"pay_74b64219bcefebba218ac471e896",
							"pay_91b81cba9aab53781df404755736" ],
					"subscription" : null
				},
				"preauthorization" : null
			}
		},
		"created_at" : 1359642765
	}
}
package com.online.store.Config;


import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.secret.key}")
    private String _secretStripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = _secretStripeKey;
    }
}
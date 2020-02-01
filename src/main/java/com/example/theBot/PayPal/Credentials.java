package com.example.theBot.PayPal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

public class Credentials {
    static String clientId = "clientid";
    static String secret = "secret";
    private static PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, secret);
    public static PayPalHttpClient client = new PayPalHttpClient(environment);
}

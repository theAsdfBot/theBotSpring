package com.example.theBot.PayPal;

import com.paypal.http.HttpResponse;
import com.paypal.orders.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderHandler {
    public static OrderRequest createOrderRequest () {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        ApplicationContext applicationContext = new ApplicationContext().brandName("Discord.RSS").landingPage("BILLING");
        orderRequest.applicationContext(applicationContext);
        List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>();
        purchaseUnits.add(new PurchaseUnitRequest().referenceId("PUF").description("Description here").customId("CustomIDHere").softDescriptor("softDescriptor").amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("1.00")));
        orderRequest.purchaseUnits(purchaseUnits);
        return orderRequest;
    }

    public static Order executeOrderRequest (OrderRequest orderRequest) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);
        request.prefer("return=representation");
        HttpResponse<Order> response = Credentials.client.execute(request);
        Order order = response.result();
        System.out.println("Order ID: " + order.id());
        order.links().forEach(link -> System.out.println(link.rel() + " => " + link.method() + ":" + link.href()));
        return order;
    }

    public static Order executeOrderCapture (String orderID) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderID);
        request.requestBody(new OrderRequest());
        HttpResponse<Order> response = Credentials.client.execute(request);
        return response.result();
    }
}

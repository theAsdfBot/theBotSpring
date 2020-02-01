package com.example.theBot;

import com.example.theBot.PayPal.OrderHandler;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {
    @Autowired
    private ProductKeyRepository keyRepository;

    @RequestMapping(value = "/")
    public String index() {
        return "Greetings, tiny human!";
    }

    @RequestMapping(value = "/add")
    public ProductKey createKey() {
        ProductKey key = new ProductKey();
        keyRepository.save(key);
        return key;
    }

    @RequestMapping(value = "/api/create-order", method = RequestMethod.GET)
    public String createPayment() {
        OrderRequest orderRequest = OrderHandler.createOrderRequest();
        try {
            Order order = OrderHandler.executeOrderRequest(orderRequest);
            return order.id();
        } catch (IOException ioe) {
            if (ioe instanceof HttpException) {
                // Something went wrong server-side
                HttpException he = (HttpException) ioe;
                System.out.println(he.getMessage());
                he.headers().forEach(x -> System.out.println(x + " :" + he.headers().header(x)));
            } else {
                ioe.printStackTrace();
                // Something went wrong client-side
            }
            return "Something bad happened";
        }
    }

    @RequestMapping(value = "/api/capture-order/{orderID}", method = RequestMethod.GET)
    public String captureOrder(@PathVariable(value = "orderID") String orderID) {
        try {
            Order order = OrderHandler.executeOrderCapture(orderID);
            System.out.println(order.toString());
            return "ok!";
        } catch (IOException e) {
            if (e instanceof HttpException) {
                HttpException he = (HttpException) e;
                System.out.println(he.getMessage());
                he.headers().forEach(x -> System.out.println(x + " :" + he.headers().header(x)));
            }
            e.printStackTrace();
            return "Something bad happened";
        }
    }

    @RequestMapping("/all")
    public Iterable<ProductKey> getAllKeys() {
        return keyRepository.findAll();
    }
}
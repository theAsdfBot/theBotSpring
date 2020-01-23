package com.example.theBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private ProductKeyRepository keyRepository;

    @RequestMapping("/")
    public String index() {
        return "Greetings, tiny human!";
    }

    @RequestMapping("/add")
    public ProductKey createKey() {
        ProductKey key = new ProductKey();
        keyRepository.save(key);
        return key;
    }

    @RequestMapping("/all")
    public Iterable<ProductKey> getAllKeys() {
        return keyRepository.findAll();
    }
}
package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @RequestMapping("/hello")
    public message sayHello() {
        return new message("Hello World");
    }
}

package com.api.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.models.Greeting;

import javax.ws.rs.core.Response;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return Response.ok(new Greeting(counter.incrementAndGet(), String.format(template, name))).build();
    }
}
package com.cs125.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/")
public class HealthCheckController {
    @GetMapping
    public Response getAllUsers() {
        return Response.ok().build();
    }
}

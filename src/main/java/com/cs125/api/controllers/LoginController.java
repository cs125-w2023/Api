package com.cs125.api.controllers;

import com.cs125.api.entities.User;
import com.cs125.api.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs125.api.models.LoginDto;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Response login(@RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto);
        return Response.ok(user).build();
    }
}

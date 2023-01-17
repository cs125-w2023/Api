package com.cs125.api.controllers;

import java.util.concurrent.atomic.AtomicLong;

import com.cs125.api.entities.User;
import com.cs125.api.models.NewUserDto;
import com.cs125.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public Response getAllUsers() {
        return Response.ok(userRepository.findAll()).build();
    }

    @PostMapping
    public Response addNewUser(@RequestBody NewUserDto newUserDto) {
        User newUser = new User();
        newUser.setName(newUserDto.getName());
        userRepository.save(newUser);
        return Response.ok(newUser).build();
    }
}

package com.cs125.api.controllers;

import com.cs125.api.models.UserInfoDto;
import com.cs125.api.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs125.api.entities.User;
import com.cs125.api.models.NewUserDto;

import javax.ws.rs.core.Response;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        return Response.ok(users).build();
    }

    @PostMapping
    public Response addNewUser(@RequestBody NewUserDto newUserDto) {
        userService.addUser(newUserDto);
        return Response.ok().build();
    }

    @PutMapping("/{id}/info")
    public Response saveUserInfo(@RequestBody UserInfoDto userInfoDto, @PathVariable Long id) {
        User user = userService.saveUserInfo(userInfoDto, id);
        return Response.ok(user).build();
    }
}

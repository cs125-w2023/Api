package com.cs125.api.services.interfaces;

import com.cs125.api.entities.User;
import com.cs125.api.models.LoginDto;
import com.cs125.api.models.NewUserDto;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void addUser(NewUserDto newUserDto);
    User login(LoginDto loginDto);
}

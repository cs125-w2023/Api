package com.cs125.api.services;

import com.cs125.api.entities.User;
import com.cs125.api.models.LoginDto;
import com.cs125.api.models.NewUserDto;

import com.cs125.api.services.exceptions.InvalidLoginException;
import com.cs125.api.services.exceptions.UserExistsException;
import com.cs125.api.services.exceptions.UserNotFoundException;

import com.cs125.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.cs125.api.services.interfaces.UserService;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(NewUserDto newUserDto) {
        if (userRepository.existsByEmail(newUserDto.getEmail()))
            throw new UserExistsException();

        // bCrypt hashing
        BCrypt bCrypt = new BCrypt();
        String salt = bCrypt.gensalt(10);
        String hashedPwd = bCrypt.hashpw(newUserDto.getPassword(), salt);

        // save new user
        User newUser = new User();
        newUser.setEmail(newUserDto.getEmail());
        newUser.setPassword(hashedPwd);
        userRepository.save(newUser);
    }

    public User login(LoginDto loginDto) {
        if (!userRepository.existsByEmail(loginDto.getEmail()))
            throw new UserNotFoundException();

        // validate password
        BCrypt bCrypt = new BCrypt();
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (!bCrypt.checkpw(loginDto.getPassword(), user.getPassword()))
            throw new InvalidLoginException();

        return user;
    }
}

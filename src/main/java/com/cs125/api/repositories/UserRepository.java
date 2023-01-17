package com.cs125.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs125.api.entities.User;

public interface UserRepository extends JpaRepository<User, Long> { }

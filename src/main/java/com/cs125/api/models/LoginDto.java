package com.cs125.api.models;

import lombok.Data;

@Data
public class LoginDto {
    String email;
    String password;
}

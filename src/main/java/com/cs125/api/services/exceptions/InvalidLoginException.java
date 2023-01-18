package com.cs125.api.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid login credentials.")
public class InvalidLoginException extends RuntimeException{}

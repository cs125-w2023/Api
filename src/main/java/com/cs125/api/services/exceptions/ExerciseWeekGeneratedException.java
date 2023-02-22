package com.cs125.api.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Weekly exercise routine already generated.")
public class ExerciseWeekGeneratedException extends RuntimeException{}

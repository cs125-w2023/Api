package com.cs125.api.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Failed to generate weekly exercise routine.")
public class ExerciseWeekGenerationException extends RuntimeException{}

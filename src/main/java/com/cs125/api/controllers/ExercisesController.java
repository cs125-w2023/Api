package com.cs125.api.controllers;

import com.cs125.api.entities.Exercise;
import com.cs125.api.services.interfaces.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExercisesController {

    @Autowired
    ExerciseService exerciseService;

    @GetMapping
    public Response getWeek(@RequestHeader("userId") long userId) {
        List<Exercise> exerciseList = exerciseService.getWeek(userId);
        return Response.ok(exerciseList).build();
    }

    @PostMapping("/generate-week")
    public Response generateWeek(@RequestHeader("userId") long userId) {
        List<Exercise> exerciseList = exerciseService.generateWeek(userId);
        return Response.ok(exerciseList).build();
    }
}

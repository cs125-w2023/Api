package com.cs125.api.services.interfaces;

import com.cs125.api.entities.Exercise;

import java.util.List;

public interface ExerciseService {
    List<Exercise> getWeek(long userId);
    List<Exercise> generateWeek(long userId);
    void markComplete(long id);
}

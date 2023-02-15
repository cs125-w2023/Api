package com.cs125.api.services.interfaces;

import com.cs125.api.entities.Exercise;

import java.util.List;

public interface ExerciseService {
    List<Exercise> generateWeek(long userId);
}

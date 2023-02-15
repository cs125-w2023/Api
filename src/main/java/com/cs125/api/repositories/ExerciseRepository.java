package com.cs125.api.repositories;

import com.cs125.api.entities.BodyType;
import com.cs125.api.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {}

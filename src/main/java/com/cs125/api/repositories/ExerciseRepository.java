package com.cs125.api.repositories;

import com.cs125.api.entities.BodyType;
import com.cs125.api.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByDateAfter(Date date);
}

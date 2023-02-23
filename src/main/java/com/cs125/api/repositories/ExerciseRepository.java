package com.cs125.api.repositories;

import com.cs125.api.entities.Exercise;
import com.cs125.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByDateAfterAndUser(Date date, User user);
}

package com.cs125.constants;

import java.util.ArrayList;
import java.util.List;

public class constants {
    // body type names
    public static String BODYTYPE_SLIM = "Slim";
    public static String BODYTYPE_FIT = "Fit";
    public static String BODYTYPE_HEAVY = "Heavy";

    // types of exercises based on body type on a given day
    public static List<String> EXERCISES_SLIM = new ArrayList<>(List.of("strength", "stretching", "strength", "stretching", "strength", "stretching", "cardio"));
    public static List<String> EXERCISES_SLIM_MUSCLES = new ArrayList<>(List.of("chest,chest,triceps", ",,", "middle_back,lower_back,lats,biceps",
            ",,,", "traps,quadriceps,quadriceps", ",,", "quadriceps,abdominals"));
    public static String EXERCISES_SLIM_DIFFICULTY = "intermediate";
    public static List<String> EXERCISES_FIT = new ArrayList<>(List.of("strength", "cardio", "strength", "cardio", "strength", "strength", "stretching"));
    public static List<String> EXERCISES_FIT_MUSCLES = new ArrayList<>(List.of("chest,chest,triceps", "quadriceps,abdominals", "middle_back,lower_back,lats,biceps",
            "quadriceps,abdominals", "traps,quadriceps,quadriceps", "chest,quadriceps,middle_back,biceps", ",,"));
    public static String EXERCISES_FIT_DIFFICULTY = "expert";
    public static List<String> EXERCISES_HEAVY = new ArrayList<>(List.of("cardio", "strength", "cardio", "stretching", "cardio", "strength", "cardio"));
    public static List<String> EXERCISES_HEAVY_MUSCLES = new ArrayList<>(List.of("quadriceps,abdominals", "chest,chest,triceps", "quadriceps,abdominals",
            ",,,", "quadriceps,abdominals", "middle_back,lower_back,lats,biceps", "quadriceps,abdominals"));
    public static String EXERCISES_HEAVY_DIFFICULTY = "beginner";

    public static int NUM_DAYS = 7;

}

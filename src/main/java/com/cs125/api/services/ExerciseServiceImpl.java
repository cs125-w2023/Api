package com.cs125.api.services;

import com.cs125.api.entities.Exercise;
import com.cs125.api.entities.User;
import com.cs125.api.repositories.ExerciseRepository;
import com.cs125.api.repositories.UserRepository;
import com.cs125.api.services.exceptions.ExerciseWeekGeneratedException;
import com.cs125.api.services.exceptions.ExerciseWeekGenerationException;
import com.cs125.api.services.exceptions.ExerciseWeekNotGeneratedException;
import com.cs125.api.services.interfaces.ExerciseService;
import com.mysql.cj.xdevapi.JsonArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static com.cs125.constants.constants.BODYTYPE_FIT;
import static com.cs125.constants.constants.BODYTYPE_SLIM;
import static com.cs125.constants.constants.EXERCISES_FIT;
import static com.cs125.constants.constants.EXERCISES_FIT_DIFFICULTY;
import static com.cs125.constants.constants.EXERCISES_FIT_MUSCLES;
import static com.cs125.constants.constants.EXERCISES_HEAVY;
import static com.cs125.constants.constants.EXERCISES_HEAVY_DIFFICULTY;
import static com.cs125.constants.constants.EXERCISES_HEAVY_MUSCLES;
import static com.cs125.constants.constants.EXERCISES_SLIM;
import static com.cs125.constants.constants.EXERCISES_SLIM_DIFFICULTY;
import static com.cs125.constants.constants.EXERCISES_SLIM_MUSCLES;
import static com.cs125.constants.constants.NUM_DAYS;

import org.apache.http.impl.client.CloseableHttpClient;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Value("${apis.exercise}")
    private String exerciseApi;

    @Value("${apikeys.exercise}")
    private String exerciseApiKey;

    public List<Exercise> getWeek(long userId) {
        User user = userRepository.findById(userId).get();

        Calendar calendar = getWeeksCalendar();
        int currDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, -currDay - 1);
        List<Exercise> exerciseList = exerciseRepository.findByDateAfterAndUser(calendar.getTime(), user);

        if (exerciseList.isEmpty())
            throw new ExerciseWeekNotGeneratedException();

        return exerciseList;
    }

    public List<Exercise> generateWeek(long userId) {
        User user = userRepository.findById(userId).get();
        List<Exercise> exerciseList;

        // generate exercise list based on body type
        if (user.getBodyType().getType().equals(BODYTYPE_SLIM)) {
            exerciseList = generateWeek(EXERCISES_SLIM, EXERCISES_SLIM_MUSCLES, EXERCISES_SLIM_DIFFICULTY, user);
        }
        else if (user.getBodyType().getType().equals(BODYTYPE_FIT)) {
            exerciseList = generateWeek(EXERCISES_FIT, EXERCISES_FIT_MUSCLES, EXERCISES_FIT_DIFFICULTY, user);
        }
        else {
            exerciseList = generateWeek(EXERCISES_HEAVY, EXERCISES_HEAVY_MUSCLES, EXERCISES_HEAVY_DIFFICULTY, user);
        }

        exerciseRepository.saveAll(exerciseList);
        return exerciseList;
    }

    private List<Exercise> generateWeek(List<String> exercises, List<String> muscles, String difficulty, User user) {
        Calendar calendar = getWeeksCalendar();
        int currDay = calendar.get(Calendar.DAY_OF_WEEK);

        if (isThisWeekGenerated(user))
            throw new ExerciseWeekGeneratedException();

        try {
            // create http client and set api key
            List<Exercise> exerciseList = new ArrayList<>();
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet();
            request.setHeader("X-Api-Key", exerciseApiKey);

            // loop through the remaining days this week including today and create an exercise for it
            for (int i = currDay - 1; i < NUM_DAYS; i++) {
                String type = exercises.get(i);
                Dictionary<String, String> chosen = new Hashtable<>();
                for (String muscle : muscles.get(i).split(",", -1)) {
                    URIBuilder builder = new URIBuilder(exerciseApi);
                    builder.setParameter("type", type).setParameter("muscle", muscle).setParameter("difficulty", difficulty);
                    JSONObject jsonObject = sendExerciseApiRequest(httpClient, request, builder.build(), chosen);
                    Exercise exercise = jsonObjectToExercise(jsonObject, calendar.getTime());
                    exercise.setUser(user);
                    exerciseList.add(exercise);
                }
                calendar.add(Calendar.DATE, 1);
            }

            return exerciseList;
        }
        catch (Exception ex) {
            throw new ExerciseWeekGenerationException();
        }
    }

    private Calendar getWeeksCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private boolean isThisWeekGenerated(User user) {
        Calendar calendar = getWeeksCalendar();
        int currDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, -currDay - 1);
        return !exerciseRepository.findByDateAfterAndUser(calendar.getTime(), user).isEmpty();
    }

    // send an api request and return a random result
    private JSONObject sendExerciseApiRequest(CloseableHttpClient client, HttpGet request, URI uri, Dictionary<String, String> chosen) throws IOException,
            ParseException {
        request.setURI(uri);
        HttpResponse result = client.execute(request);

        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        JSONArray jsonArray = (JSONArray) resultObject;

        // find a result that isn't chosen already
        boolean found = false;
        JSONObject jsonObject = null;
        while (!found) {
            jsonObject = (JSONObject) jsonArray.get((int) (Math.random() * (jsonArray.size()-1)));
            String name = jsonObject.get("name").toString();
            if (chosen.get(name) == null) {
                chosen.put(name, name);
                found = true;
            }
        }

        return jsonObject;
    }

    private Exercise jsonObjectToExercise(JSONObject jsonObject, Date date) {
        Exercise exercise = new Exercise();
        exercise.setName(jsonObject.get("name").toString());
        exercise.setType(jsonObject.get("type").toString());
        exercise.setMuscle(jsonObject.get("muscle").toString());
        exercise.setEquipment(jsonObject.get("equipment").toString());
        exercise.setDifficulty(jsonObject.get("difficulty").toString());
        exercise.setInstructions("");
        exercise.setDate(date);
        exercise.setCompleted(false);
        return exercise;
    }
}

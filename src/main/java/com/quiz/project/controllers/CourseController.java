package com.quiz.project.controllers;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.quiz.project.models.Course;
import java.util.ArrayList;
import org.bson.Document;

public class CourseController {

    public static MongoCollection<Document> getCoursesCollection() {
        return MongoConector.getCollection("course");
    }

    public static ArrayList<Course> getAllCourses() {
        ArrayList<Course> result = new ArrayList<>();
        FindIterable<Document> courses =  getCoursesCollection().find();
        for (Document cours : courses) {
            String coursJson = cours.toJson();
            Gson gson = new Gson();
            Course course =  gson.fromJson(coursJson, Course.class);
            result.add(course);
        }
        return result;
    }
}

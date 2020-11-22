package com.quiz.project.api;

import com.quiz.project.controllers.CourseController;
import com.quiz.project.models.Course;
import java.util.ArrayList;

import static spark.Spark.get;


public class Main {
    public static void main(String[] args) {
        System.out.println(" server running in -> http://localhost:4567");
        ArrayList<Course> courses = CourseController.getAllCourses();

        for (Course cours : courses) {
            System.out.println(cours.toString());
        }
        //get("/hello", (req, res) -> "Quiz project");
    }
}

package com.quiz.project.api;

import static spark.Spark.get;
import static spark.Spark.put;

import com.quiz.project.controllers.QuestionController;
import com.quiz.project.controllers.StudentController;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });

        System.out.println(" server running in -> http://localhost:4567");

        get("/hello", (req, res) -> "Quiz project");


        put("/login", ((req, res) -> {
            res.type("application/json");
            return StudentController.login(req.body());
        }));

        put("/register", ((req, res) -> {
            res.type("application/json");
            return StudentController.register(req.body());
        }));

        put("/seen/:studentId", ((req, res) -> {
            int studentId = Integer.parseInt(req.params(":studentId"));
            res.type("application/json");
            return StudentController.addSeen(studentId, req.body()).toJson();
        }));

        put("/result/:studentId", ((req, res) -> {
            int studentId = Integer.parseInt(req.params(":studentId"));
            res.type("application/json");
            return StudentController.addResult(studentId, req.body()).toJson();
        }));


        get("/questions/:course/:amount", (req, res) -> {
            res.type("application/json");
            return QuestionController
                    .getRandomQuestions(req.params(":course"),
                            Integer.parseInt(req.params(":amount")));
        });

        get("/questions/:course/:dificulty/:amount", (req, res) -> {
            res.type("application/json");
            return QuestionController
                    .getRandomQuestions(req.params(":course"),
                            req.params(":dificulty"),
                            Integer.parseInt(req.params(":amount")));
        });
    }
}

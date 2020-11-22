package com.quiz.project.api;

import static spark.Spark.get;
import static spark.Spark.put;

import com.quiz.project.controllers.QuestionController;

public class Main {
    public static void main(String[] args) {
        System.out.println(" server running in -> http://localhost:4567");

        get("/hello", (req, res) -> "Quiz project");

        put("/hello", ((req, res) -> {
            System.out.println(req.body());
            return "ok";
        }));



        put("/login", ((req, res) -> {
            System.out.println(req.body());
            return "ok";
        }));


        get("/questions/:course/:amount", (req, res) -> {
            res.type("application/json");
            return QuestionController
                    .getRandomQuestions(req.params(":course"),
                            Integer.parseInt(req.params(":amount")));
        });
    }
}

package com.quiz.project.api;

import static spark.Spark.get;


public class Main {
    public static void main(String[] args) {
        System.out.println(" server running in -> http://localhost:4567");
        get("/hello", (req, res) -> "Quiz project");
    }
}

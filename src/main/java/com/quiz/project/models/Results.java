package com.quiz.project.models;

public class Results {

    public int id;
    public int score;
    public Dificulty dificulty;

    public enum Dificulty {
        EASY, MEDIUM, HARD
    }

    public int courseName;
}

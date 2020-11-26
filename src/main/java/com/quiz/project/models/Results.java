package com.quiz.project.models;

public class Results {

    public int id;
    public String score;
    public Dificulty dificulty;

    public enum Dificulty {
        EASY, MEDIUM, HARD
    }

    public String courseName;
}

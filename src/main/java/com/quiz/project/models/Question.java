package com.quiz.project.models;

import java.util.ArrayList;

public class Question {
    int id;
    String text;
    String course;

    enum Dificulty {
        EASY, MEDIUM, HARD
    }

    ArrayList<Answer> answers;
}

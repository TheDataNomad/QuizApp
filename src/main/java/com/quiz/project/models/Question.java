package com.quiz.project.models;

import java.util.ArrayList;

public class Question {
    int id;
    String text;

    enum Dificulty {
        EASY, MEDIUM, HARD
    }

    ArrayList<Answer> answers;
}

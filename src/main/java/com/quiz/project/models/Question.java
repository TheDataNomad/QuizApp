package com.quiz.project.models;

import com.google.gson.Gson;
import java.util.ArrayList;
import org.bson.types.ObjectId;

public class Question {
    public Integer id;
    public String text;
    public String course;
    public Dificulty dificulty;

    public enum Dificulty {
        EASY, MEDIUM, HARD
    }

    public ArrayList<Answer> answers;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

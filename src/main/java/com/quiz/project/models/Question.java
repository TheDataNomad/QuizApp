package com.quiz.project.models;

import com.google.gson.Gson;
import java.util.ArrayList;
import org.bson.types.ObjectId;

public class Question {
    public Integer id;
    public String question;
    public String course;
    public Dificulty dificulty;

    public enum Dificulty {
            easy, medium, hard
    }

    public String correct;
    public ArrayList<String> incorrect;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

package com.quiz.project.models;


import com.google.gson.Gson;

public class Course {
    public int id;
    public String name;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

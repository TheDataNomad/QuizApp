package com.quiz.project.models;

import com.google.gson.Gson;
import org.bson.Document;

public class Credentials {
    public String user;
    public String password;


    public static Credentials fromJson(String credentialsJson) {
        Gson gson = new Gson();
        return gson.fromJson(credentialsJson, Credentials.class);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

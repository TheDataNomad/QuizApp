package com.quiz.project.models;

import com.google.gson.Gson;
import java.util.ArrayList;
import org.bson.Document;

public class Student {
    public int id;
    public String name;
    public String password;

    public ArrayList<Integer> seen;

    public ArrayList<Results> results;

    public static Student fromDocument(Document doc) {
        String studentJson = doc.toJson();
        Gson gson = new Gson();
        return gson.fromJson(studentJson, Student.class);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public  Document toDocument() {
        return  Document.parse(this.toString());
    }
}

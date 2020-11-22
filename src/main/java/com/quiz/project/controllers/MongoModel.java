package com.quiz.project.controllers;

import com.google.gson.Gson;
import org.bson.Document;

public interface MongoModel<T> {

    T fromDocument(Document doc);

    Document toDocument();

}

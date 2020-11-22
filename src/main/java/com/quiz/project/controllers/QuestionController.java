package com.quiz.project.controllers;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.quiz.project.models.Question;
import java.util.ArrayList;
import org.bson.Document;

public class QuestionController {
    public static final String COLLECTION_NAME = "questions";

    public static MongoCollection<Document> getQuestionsCollection() {
        return MongoConector.getCollection(COLLECTION_NAME);
    }

    public static ArrayList<Question> getAllQuestions() {
        ArrayList<Question> result = new ArrayList<>();
        FindIterable<Document> questions =  getQuestionsCollection().find();
        for (Document questionDoc : questions) {
            String questionJson = questionDoc.toJson();
            Gson gson = new Gson();
            Question question =  gson.fromJson(questionJson, Question.class);
            result.add(question);
        }
        return result;
    }

    public static Integer saveQuestion(Question question) {

        question.id = MongoConector.getNextIndex(COLLECTION_NAME);

        Document questionDoc =  Document.parse(question.toString());
        MongoCollection<Document> questionCol = getQuestionsCollection();
        questionCol.insertOne(questionDoc);

        return (Integer) questionDoc.get("id");
    }

    public static ArrayList<Question> getAllQuestionsFromCourse(String course) {
        ArrayList<Question> result = new ArrayList<>();
        FindIterable<Document> questions =  getQuestionsCollection().find(
                Filters.eq("course", course)
        );
        for (Document questionDoc : questions) {
            String questionJson = questionDoc.toJson();
            Gson gson = new Gson();
            Question question =  gson.fromJson(questionJson, Question.class);
            result.add(question);
        }
        return result;
    }

    public static  void  removeQuestion(int id) {
        getQuestionsCollection().deleteOne(Filters.eq("id", id));
    }

    public static void  removeAllQuestionsFromCourse(String course) {
        getQuestionsCollection().deleteMany(Filters.eq("course", course));
    }

}

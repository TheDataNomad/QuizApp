package com.quiz.project.controllers;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.quiz.project.models.Course;
import com.quiz.project.models.Question;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
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

    public static ArrayList<Question> getQuestions(String course, String dificulty) {
        ArrayList<Question> result = new ArrayList<>();
        FindIterable<Document> questions =  getQuestionsCollection().find(
                Filters.and(Filters.eq("course", course),
                            Filters.eq("difficulty", dificulty.toLowerCase()))
        );
        for (Document questionDoc : questions) {
            String questionJson = questionDoc.toJson();
            Gson gson = new Gson();
            Question question =  gson.fromJson(questionJson, Question.class);
            result.add(question);
        }
        return result;
    }

    public static Document updateQuestion(int id, Question question) {
        Document questionDoc =  Document.parse(question.toString());
        return getQuestionsCollection().findOneAndUpdate(Filters.eq("id", id), questionDoc);
    }

    public static  void  removeQuestion(int id) {
        getQuestionsCollection().deleteOne(Filters.eq("id", id));
    }

    public static void  removeAllQuestionsFromCourse(String course) {
        getQuestionsCollection().deleteMany(Filters.eq("course", course));
    }

    public static String getRandomQuestions(String course, int amount) {
        ArrayList<Question> all =  getAllQuestionsFromCourse(course);
        System.out.println("getting " + amount + " from " + course);
        //not optimal but its a funny way
        while (all.size() > amount) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, all.size());
            all.remove(randomNum);
        }

        Gson gson = new Gson();
        return gson.toJson(all);
    }

    public static String getRandomQuestions(String course, String dificulty, int amount) {
        ArrayList<Question> all =  getQuestions(course, dificulty);
        System.out.println("getting " + amount + " from " + course);
        //not optimal but its a funny way
        while (all.size() > amount) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, all.size());
            all.remove(randomNum);
        }

        Gson gson = new Gson();
        return gson.toJson(all);
    }
}

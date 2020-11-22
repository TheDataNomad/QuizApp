package com.quiz.project.controllers;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.quiz.project.models.Results;
import com.quiz.project.models.Student;
import java.util.ArrayList;
import org.bson.Document;


public class StudentController {
    public static final String COLLECTION_NAME = "students";

    public static MongoCollection<Document> getCollection() {
        return MongoConector.getCollection(COLLECTION_NAME);
    }
    
    public static Integer saveStudent(Student student) {

        student.id = MongoConector.getNextIndex(COLLECTION_NAME);

        Document studenDoc =  Document.parse(student.toString());
        MongoCollection<Document> studenCol = getCollection();
        studenCol.insertOne(studenDoc);
        return (Integer) studenDoc.get("id");
    }
    
    public static Student getStudentFromName(String name) {
        Document studentDoc =  getCollection().find(
                Filters.eq("name", name)
        ).first();

        String studentJson = studentDoc.toJson();
        Gson gson = new Gson();
        return   gson.fromJson(studentJson, Student.class);
    }

    public static Student getStudentFromId(int id) {
        Document studentDoc =  getCollection().find(
                Filters.eq("id", id)
        ).first();

        String studentJson = studentDoc.toJson();
        Gson gson = new Gson();
        return   gson.fromJson(studentJson, Student.class);
    }

    //addScore
    public Document addResult(int studentId, Results results) {
        Student toChange = getStudentFromId(studentId);

        // autoincrement For more control
        results.id = MongoConector.getNextIndex("results");
        toChange.results.add(results);

        return getCollection().findOneAndUpdate(Filters.eq("id", studentId), toChange.toDocument());

    }

    //removeScore
    public Document removeResult(int studentId, int resultId) {

        Student toChange = getStudentFromId(studentId);

        toChange.results.removeIf(result -> result.id == resultId);

        return getCollection().findOneAndUpdate(Filters.eq("id", studentId), toChange.toDocument());
    }

    //getScores
    public ArrayList<Results> getResults(int studentId) {
        Student student = getStudentFromId(studentId);
        return student.results;
    }

    //getAnsweredQuizList
    public ArrayList<Integer> getAnsweredQuizList(int studentId) {
        Student student = getStudentFromId(studentId);
        return student.seen;
    }

    //addAnsewredQuiz
    public Document addAnsewredQuiz(int studentId, Integer quizId) {
        Student toChange = getStudentFromId(studentId);
        toChange.seen.add(quizId);
        return getCollection().findOneAndUpdate(Filters.eq("id", studentId), toChange.toDocument());
    }
}

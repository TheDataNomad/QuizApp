package com.quiz.project.controllers;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.quiz.project.models.Credentials;
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

        if (studentDoc == null) {
            return null;
        }
        String studentJson = studentDoc.toJson();

        Gson gson = new Gson();
        return   gson.fromJson(studentJson, Student.class);
    }

    public static Student getStudentFromId(int id) {
        Document studentDoc =  getCollection().find(
                Filters.eq("id", id)
        ).first();
        if (studentDoc == null) {
            return null;
        }
        String studentJson = studentDoc.toJson();
        Gson gson = new Gson();
        return   gson.fromJson(studentJson, Student.class);
    }

    public static String login(String user, String password) {
        Student student = getStudentFromName(user);

        if (student != null && password.equals(student.password)) {
            return "{ \"message\" : \"success\"}";
        }
        return "{ \"message\" : \"failure\"}";
    }


    public static String register(String body) {
        Credentials cred = Credentials.fromJson(body);
        Student student = getStudentFromName(cred.user);

        if (student == null) {
            return createNewUser(cred.user, cred.password).toString();
        }
        return "{ \"error\" : \"student already exist\"}";
    }

    private static Student createNewUser(String user, String password) {
        Student student = new Student(user, password);
        student.id = saveStudent(student);
        return student;
    }

    public static Document addSeen(int studentId, String body) {
        Student toChange = getStudentFromId(studentId);
        assert toChange != null;
        Gson gson = new Gson();
        ArrayList newSeen = gson.fromJson(body, ArrayList.class);
        for (Object o : newSeen) {
            Float seen = Float.parseFloat(o.toString());
            toChange.seen.add(seen.intValue());
        }
        getCollection().replaceOne(Filters.eq("id", studentId), toChange.toDocument());

        return toChange.toDocument();
    }

    //addScore
    public static Document addResult(int studentId, String jsonResult) {
        Gson gson = new Gson();
        Results results = gson.fromJson(jsonResult, Results.class);

        Student toChange = getStudentFromId(studentId);

        // autoincrement For more control
        results.id = MongoConector.getNextIndex("results");
        assert toChange != null;
        toChange.results.add(results);

        getCollection().replaceOne(Filters.eq("id", studentId), toChange.toDocument());
        return toChange.toDocument();
    }

    //removeScore
    public Document removeResult(int studentId, int resultId) {

        Student toChange = getStudentFromId(studentId);

        assert toChange != null;
        toChange.results.removeIf(result -> result.id == resultId);

        return getCollection().findOneAndUpdate(Filters.eq("id", studentId), toChange.toDocument());
    }

    //getScores
    public ArrayList<Results> getResults(int studentId) {
        Student student = getStudentFromId(studentId);
        assert student != null;
        return student.results;
    }

    //getAnsweredQuizList
    public ArrayList<Integer> getAnsweredQuizList(int studentId) {
        Student student = getStudentFromId(studentId);
        assert student != null;
        return student.seen;
    }

    //addAnsewredQuiz
    public Document addAnsewredQuiz(int studentId, Integer quizId) {
        Student toChange = getStudentFromId(studentId);
        assert toChange != null;
        toChange.seen.add(quizId);
        return getCollection().findOneAndUpdate(Filters.eq("id", studentId), toChange.toDocument());
    }
}

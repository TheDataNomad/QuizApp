package com.quiz.project.controllers;

import com.quiz.project.models.Answer;
import com.quiz.project.models.Question;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.bson.types.ObjectId;
import org.junit.Assert;

public class QuestionControllerTest extends TestCase {

    public void testGetAllQuestions() {
        ArrayList<Question> questions = QuestionController.getAllQuestions();
        for (Question question : questions) {
            System.out.println(question.toString());
        }

        Assert.assertFalse(questions.isEmpty());
    }

    public void testInsertQuestion() {
        Answer a1 = new Answer();
        a1.text = "test Answer 1";
        a1.correct = true;

        Answer a2 = new Answer();
        a2.text = "test Answer 2";
        a2.correct = false;

        Answer a3 = new Answer();
        a3.text = "test Answer 3";
        a3.correct = false;

        Question q1 = new Question();

        q1.course = "TestApi";
        q1.text = "will this be added ?";
        q1.dificulty = Question.Dificulty.MEDIUM;

        q1.answers = new ArrayList<>();

        q1.answers.add(a1);
        q1.answers.add(a2);
        q1.answers.add(a3);


        Integer id = QuestionController.saveQuestion(q1);
        Assert.assertNotNull(id);
    }


    public void testDeleteAllQuestionsFromCourse() {
        ArrayList<Question> questions = QuestionController.getAllQuestionsFromCourse("TestApi");
        for (Question question : questions) {
            System.out.println(question.toString());
        }

        Assert.assertFalse(questions.isEmpty());

        QuestionController.removeAllQuestionsFromCourse("TestApi");

        questions = QuestionController.getAllQuestionsFromCourse("TestApi");
        Assert.assertTrue(questions.isEmpty());

    }
}
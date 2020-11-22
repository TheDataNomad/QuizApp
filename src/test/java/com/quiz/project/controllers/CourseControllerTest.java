package com.quiz.project.controllers;

import com.quiz.project.models.Course;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.Assert;

public class CourseControllerTest extends TestCase {

    public void testGetAllCourses() {
        ArrayList<Course> courses = CourseController.getAllCourses();
        for (Course cours : courses) {
            System.out.println(cours.toString());
        }
        Assert.assertFalse(courses.isEmpty());
    }
}
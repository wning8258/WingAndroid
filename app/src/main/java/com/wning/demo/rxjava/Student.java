package com.wning.demo.rxjava;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Student {

    private String name;

    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Student() {
    }

    public Student(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }
}

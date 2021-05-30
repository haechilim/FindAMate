package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    public static School school = new School();
    public static List<Student> students = new ArrayList<>();

    @Override
    public String toString() {
        return "Classroom{" +
                "school=" + school +
                ", students=" + students +
                '}';
    }
}
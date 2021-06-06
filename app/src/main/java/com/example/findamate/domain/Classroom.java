package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    public static School school = new School();
    public static List<Student> students = new ArrayList<>();
    public static List<History> histories = new ArrayList<>();

    public static int getMaxRound() {
        return histories.size();
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "school=" + school +
                ", students=" + students +
                '}';
    }
}
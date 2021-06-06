package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    public static School school = new School();
    public static List<Student> students = new ArrayList<>();
    public static List<History> histories = new ArrayList<>();

    public static String getClassInfo() {
        return String.format("%s %s학년 %s반", school.getName(), school.getYear(), school.getNumber());
    }

    public static Student findStudentById(int id) {
        if(id == -1) return null;

        for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if(student.getId() == id) return student;
        }

        return null;
    }

    public static int getMaxRound() {
        return histories.size() + 1;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "school=" + school +
                ", students=" + students +
                '}';
    }
}
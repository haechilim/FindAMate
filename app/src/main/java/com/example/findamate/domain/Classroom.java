package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    public static School school = new School();
    public static List<Student> students = new ArrayList<>();
    public static List<History> histories = new ArrayList<>();
    public static List<Student> clonedStudents = new ArrayList<>();
    public static List<History> clonedHistories = new ArrayList<>();

    public static String getClassInfo() {
        return String.format("%s %s학년 %s반", school.getName(), school.getYear(), school.getNumber());
    }

    public static Student findStudentById(int id, boolean isSimulation) {
        if(id == -1) return null;

        List<Student> list = isSimulation ? clonedStudents : students;

        for(int i = 0; i < list.size(); i++) {
            Student student = list.get(i);

            if(student.getId() == id) return student;
        }

        return null;
    }

    public static int getMaxRound() {
        return histories.size() + 1;
    }

    public static List<Student> clonedStudents() {
        clonedStudents.clear();

        for(int i = 0; i < students.size(); i++) {
            clonedStudents.add(students.get(i).clone());
        }

        return clonedStudents;
    }

    public static List<History> clonedHistories() {
        clonedHistories.clear();
        return clonedHistories;

        /*clonedHistories.clear();

        for(int i = 0; i < histories.size(); i++) {
            clonedHistories.add(histories.get(i).clone());
        }

        return clonedHistories;*/
    }

    public static List<Student> getClonedStudents() {
        return clonedStudents;
    }

    public static void setClonedStudents(List<Student> clonedStudents) {
        Classroom.clonedStudents = clonedStudents;
    }

    public static List<History> getClonedHistories() {
        return clonedHistories;
    }

    public static void setClonedHistories(List<History> clonedHistories) {
        Classroom.clonedHistories = clonedHistories;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "school=" + school +
                ", students=" + students +
                '}';
    }
}
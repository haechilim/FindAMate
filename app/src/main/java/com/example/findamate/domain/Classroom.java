package com.example.findamate.domain;

import android.util.Log;

import com.example.findamate.helper.Logger;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    public static School school = new School();
    public static List<Student> students = new ArrayList<>();
    public static List<History> histories = new ArrayList<>();
    public static List<Student> clonedStudents = new ArrayList<>();
    public static List<History> clonedHistories = new ArrayList<>();
    public static List<Student> tempStudents = new ArrayList<>();
    public static List<Couple> couples = new ArrayList<>();

    public static String getClassInfo() {
        if(school.getName() == null) return "";
        if(school.getName().isEmpty()) return "학급을 설정해 주세요.";

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

    public static void calculateHappiness() {
        for(int i = 0; i < students.size(); i++) {
            students.get(i).calculateHappiness();
        }
    }

    public static int getLastRound(boolean isSimulation) {
        List<History> list = isSimulation ? getClonedHistories() : histories;
        return list.size();
    }

    public static List<Student> clonedStudents() {
        clonedStudents.clear();

        for(int i = 0; i < students.size(); i++) {
            clonedStudents.add(students.get(i).clone());
        }

        return clonedStudents;
    }

    public static List<Couple> clonedCouples() {
        List<Couple> result = new ArrayList<>();

        for(int i = 0; i < couples.size(); i++) {
            result.add(couples.get(i).clone());
        }

        return result;
    }

    public static List<History> clonedHistories() {
        clonedHistories.clear();
        return clonedHistories;
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
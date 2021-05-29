package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<StudentsPair> studentsPairList = new ArrayList<>();

    public void addStudentsPair(StudentsPair studentsPair) {
        studentsPairList.add(studentsPair);
    }

    public List<StudentsPair> getStudentsPairList() {
        return studentsPairList;
    }

    public int size() {
        return studentsPairList.size();
    }

    public StudentsPair get(int index) {
        return studentsPairList.get(index);
    }
}

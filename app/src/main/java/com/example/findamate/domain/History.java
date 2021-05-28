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
}

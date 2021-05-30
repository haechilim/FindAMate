package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatchingResult {
    private Date date;
    private List<StudentsPair> studentsPairs = new ArrayList<>();

    public MatchingResult() {}

    public MatchingResult(List<StudentsPair> studentsPairs) {
        this.studentsPairs = studentsPairs;
    }

    public void addStudentsPair(StudentsPair studentsPair) {
        studentsPairs.add(studentsPair);
    }

    public List<StudentsPair> getStudentsPairs() {
        return studentsPairs;
    }

    public int size() {
        return studentsPairs.size();
    }

    public StudentsPair get(int index) {
        return studentsPairs.get(index);
    }
}
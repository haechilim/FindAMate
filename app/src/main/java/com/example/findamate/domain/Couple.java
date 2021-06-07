package com.example.findamate.domain;

public class Couple {
    private Student student1;
    private Student student2;

    public Couple(Student student1, Student student2) {
        this.student1 = student1;
        this.student2 = student2;
    }

    public Couple clone() {
        return new Couple(student1, student2);
    }

    public Student getStudent1() {
        return student1;
    }

    public void setStudent1(Student student1) {
        this.student1 = student1;
    }

    public Student getStudent2() {
        return student2;
    }

    public void setStudent2(Student student2) {
        this.student2 = student2;
    }
}

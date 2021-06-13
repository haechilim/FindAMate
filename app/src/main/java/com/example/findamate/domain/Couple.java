package com.example.findamate.domain;

public class Couple {
    private int studentId1;
    private int studentId2;
    private Student student1;
    private Student student2;

    public Couple() {}

    public Couple(Student student1, Student student2) {
        this.student1 = student1;
        this.student2 = student2;
    }

    public Couple(int studentId1, int studentId2, Student student1, Student student2) {
        this.studentId1 = studentId1;
        this.studentId2 = studentId2;
        this.student1 = student1;
        this.student2 = student2;
    }

    public Couple clone() {
        return new Couple(studentId1, studentId2, student1, student2);
    }

    public int getStudentId1() {
        return studentId1;
    }

    public void setStudentId1(int studentId1) {
        this.studentId1 = studentId1;
    }

    public int getStudentId2() {
        return studentId2;
    }

    public void setStudentId2(int studentId2) {
        this.studentId2 = studentId2;
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

    private String toString(Student student) {
        return student != null ? String.format("%s/%s(%d)", student.getName(), student.isMale() ? "남" : "여", student.getScore()) : "";
    }

    @Override
    public String toString() {
        return String.format("{%s, %s}", toString(student1), toString(student2));
    }
}
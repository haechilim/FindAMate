package com.example.findamate.domain;

public class FavoritePartner {
    private int studentId;
    private int rank;

    public FavoritePartner(int studentId, int rank) {
        this.studentId = studentId;
        this.rank = rank;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "FavoritePartner{" +
                "studentId=" + studentId +
                ", rank=" + rank +
                '}';
    }
}

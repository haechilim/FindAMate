package com.example.findamate.domain;

public class School {
    private String name;
    private String year;
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", number=" + number +
                '}';
    }
}
package com.example.findamate.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class History {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private int id;
    private String date;
    private int agree;
    private int disagree;
    private List<Couple> couples;
    private List<Couple> clonedCouples = new ArrayList<>();

    public History() {}

    public History(List<Couple> couples) {
        this(couples, 0, 0);
    }

    public History(List<Couple> couples, int agree, int disagree) {
        this(new SimpleDateFormat(DATE_FORMAT).format(new Date()), agree, disagree, couples);
    }

    public History(String date, int agree, int disagree, List<Couple> couples) {
        this.date = date;
        this.agree = agree;
        this.disagree = disagree;
        this.couples = couples;
    }

    public History clone() {
        for(int i = 0; i < couples.size(); i++) {
            clonedCouples.add(couples.get(i).clone());
        }

        return new History(date, agree, disagree, clonedCouples);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getDisagree() {
        return disagree;
    }

    public void setDisagree(int disagree) {
        this.disagree = disagree;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        this.date = simpleDateFormat.format(date);
    }

    public List<Couple> getClonedCouples() {
        return clonedCouples;
    }

    public void setClonedCouples(List<Couple> clonedCouples) {
        this.clonedCouples = clonedCouples;
    }

    public List<Couple> getCouples() {
        return couples;
    }

    public void setCouples(List<Couple> couples) {
        this.couples = couples;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", agree=" + agree +
                ", disagree=" + disagree +
                ", couples=" + couples +
                ", clonedCouples=" + clonedCouples +
                '}';
    }
}
package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class History {
    private Calendar calendar;
    private List<Couple> couples;
    private List<Couple> clonedCouples = new ArrayList<>();

    public History(Calendar calendar, List<Couple> couples) {
        this.calendar = calendar;
        this.couples = couples;
    }

    public History clone() {
        for(int i = 0; i < couples.size(); i++) {
            clonedCouples.add(couples.get(i).clone());
        }

        return new History(calendar, clonedCouples);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public List<Couple> getCouples() {
        return couples;
    }

    public void setCouples(List<Couple> couples) {
        this.couples = couples;
    }
}
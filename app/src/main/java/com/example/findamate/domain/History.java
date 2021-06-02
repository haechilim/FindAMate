package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class History {
    private Calendar calendar;
    private List<Couple> couples = new ArrayList<>();

    public History(Calendar calendar, List<Couple> couples) {
        this.calendar = calendar;
        this.couples = couples;
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
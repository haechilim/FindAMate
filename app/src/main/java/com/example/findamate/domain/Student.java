package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private boolean hasPartner;
    private boolean male;
    private List<Student> favoritePartners = new ArrayList<>();
    private List<Student> partners = new ArrayList<>();
    private int score = 0;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(String id, String name, boolean male) {
        this.id = id;
        this.name = name;
        this.male = male;
    }

    public Student getFavoritePartner(int choice) {
        return choice >= 0 && choice < favoritePartners.size() ? favoritePartners.get(choice) : null;
    }

    public boolean isExpartner(Student partner) {
        for(int i = 0; i < partners.size(); i++) {
            if(partners.get(i).getId() == partner.getId()) return true;
        }

        return false;
    }

    public void clearHasPartner() {
        hasPartner = false;
    }

    public void addFavoritePartner(Student partner) {
        favoritePartners.add(partner);
    }

    public int getFavoritePartnerIndex(Student student) {
        for(int index = 0; index < favoritePartnersSize(); index++) {
            if(favoritePartners.get(index).getId() == student.getId()) return favoritePartnersSize() - index;
        }

        return 0;
    }

    public void addPartner(Student student) {
        partners.add(student);
    }

    public void addScore(int number) {
        score += number;
    }

    public int favoritePartnersSize() {
        return favoritePartners.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasPartner() {
        return hasPartner;
    }

    public void setHasPartner(boolean hasPartner) {
        this.hasPartner = hasPartner;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public List<Student> getFavoritePartners() {
        return favoritePartners;
    }

    public void setFavoritePartners(List<Student> favoritePartners) {
        this.favoritePartners = favoritePartners;
    }

    public List<Student> getPartners() {
        return partners;
    }

    public void setPartners(List<Student> partners) {
        this.partners = partners;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

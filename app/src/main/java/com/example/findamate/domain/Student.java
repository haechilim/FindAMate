package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
    public final static int MAX_FAVORITE_SCORE = 3; // 최대 몇지망까지 입력받을지의 값

    private int id;
    private String name;
    private boolean male;
    private String phone;
    private int avatarId;
    private int score = 0;
    private double happiness = 0.0;
    private String statusMessage = "";
    private List<FavoritePartner> favoritePartnerIds = new ArrayList<>();
    private List<Integer> partnerIds = new ArrayList<>();
    private boolean hasPartner;
    private static int sequence = 0;

    public Student() {

    }

    public Student(String name, boolean male, String phone, int avatarId) {
        this.name = name;
        this.male = male;
        this.phone = phone;
        this.avatarId = avatarId;
    }

    public Student(String name, boolean male, String phone, int avatarId, String statusMessage) {
        this.id = sequence++;
        this.name = name;
        this.male = male;
        this.phone = phone;
        this.avatarId = avatarId;
        this.statusMessage = statusMessage;
    }

    public Student(int id, String name, boolean male, String phone, int avatarId, int score, double happiness, String statusMessage, boolean hasPartner) {
        this.id = id;
        this.name = name;
        this.male = male;
        this.phone = phone;
        this.avatarId = avatarId;
        this.score = score;
        this.happiness = happiness;
        this.statusMessage = statusMessage;
        this.hasPartner = hasPartner;
    }

    public Student clone() {
        Student student = new Student(id, name, male, phone, avatarId, score, happiness, statusMessage, hasPartner);
        student.favoritePartnerIds = cloneFavoritePartnerIds();
        student.partnerIds = clonePartnerIds();
        return student;
    }

    private List<Integer> clonePartnerIds() {
        List<Integer> clonedPartnerIds = new ArrayList<>();

        for(int i = 0; i < partnerIds.size(); i++) {
            clonedPartnerIds.add(partnerIds.get(i));
        }

        return clonedPartnerIds;
    }

    private List<FavoritePartner> cloneFavoritePartnerIds() {
        List<FavoritePartner> cloneFavoritePartnerIds = new ArrayList<>();

        for(int i = 0; i < favoritePartnerIds.size(); i++) {
            cloneFavoritePartnerIds.add(favoritePartnerIds.get(i));
        }

        return cloneFavoritePartnerIds;
    }

    public int getFavoritePartnerId(int choice) {
        return choice >= 0 && choice < favoritePartnerIds.size() ? favoritePartnerIds.get(choice).getStudentId() : -1;
    }

    public boolean isExpartner(Student partner) {
        for(int i = 0; i < partnerIds.size(); i++) {
            if(partnerIds.get(i) == partner.getId()) return true;
        }

        return false;
    }

    public void clearHasPartner() {
        hasPartner = false;
    }

    public void addFavoritePartner(Student partner, int rank) {
        favoritePartnerIds.add(new FavoritePartner(partner.getId(), rank));
    }

    public int getFavoritePartnerIndex(Student student) {
        for(int index = 0; index < favoritePartnersSize(); index++) {
            FavoritePartner favoritePartner = favoritePartnerIds.get(index);
            if(favoritePartner.getStudentId() == student.getId()) return MAX_FAVORITE_SCORE - (favoritePartner.getRank() + 1);
        }

        return 0;
    }

    public void addPartner(Student student) {
        partnerIds.add(student != null ? student.getId() : -1);
    }

    public void addScore(int number) {
        score += number;
    }

    public int favoritePartnersSize() {
        return favoritePartnerIds.size();
    }

    public void calculateHappiness() {
        int round = getPartnerIds().size();
        if(score < 0 || round <= 0) happiness = 0;
        else happiness = (double)score / MAX_FAVORITE_SCORE / round * 100;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasPartner() {
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

    public List<FavoritePartner> getFavoritePartnerIds() {
        return favoritePartnerIds;
    }

    public void setFavoritePartnerIds(List<FavoritePartner> favoritePartnerIds) {
        this.favoritePartnerIds = favoritePartnerIds;
    }

    public List<Integer> getPartnerIds() {
        return partnerIds;
    }

    public void setPartnerIds(List<Integer> partnerIds) {
        this.partnerIds = partnerIds;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getHappiness() {
        return happiness;
    }

    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", male=" + male +
                ", phone='" + phone + '\'' +
                ", avatarId=" + avatarId +
                ", score=" + score +
                ", happiness=" + happiness +
                ", statusMessage='" + statusMessage + '\'' +
                ", hasPartner=" + hasPartner +
                ", favoritePartners=" + favoritePartnerIds +
                ", partnerIds=" + partnerIds +
                '}';
    }
}
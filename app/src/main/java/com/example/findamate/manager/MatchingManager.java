package com.example.findamate.manager;

import com.example.findamate.domain.Couple;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MatchingManager {
    public static final int MATCHING_MODE_DIFF = 1;
    public static final int MATCHING_MODE_SAME = 2;
    public static final int MATCHING_MODE_NONE = 3;

    private boolean isSimulation;
    private int mode;
    private boolean duplicated;
    private List<Student> students;
    private List<Couple> couples = new ArrayList<>();

    public MatchingManager(boolean isSimulation, int mode, boolean duplicated, List<Student> students) {
        this.isSimulation = isSimulation;
        this.mode = mode;
        this.duplicated = duplicated;
        this.students = students;
    }

    public boolean match() {
        if(students.size() <= 0) return false;

        sort();
        clearPartners();
        favoriteMatching();
        randomMatching(true);
        randomMatching(false);
        othersMatching();
        updateHappiness();
        Logger.debug("매칭결과 " + couples);

        return !couples.isEmpty();
    }

    private void sort() {
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getScore() - o2.getScore();
            }
        });
    }

    private void clearPartners() {
        for (int i = 0; i < students.size(); i++) {
            students.get(i).clearHasPartner();
        }
    }

    private void favoriteMatching() {
        for (int choice = 0; choice < Student.MAX_FAVORITE_SCORE; choice++) {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                Student partner = getStudent(student.getFavoritePartnerId(choice));

                if(partner == null) continue;
                if(!isSuitablePartner(student, partner)) continue;

                if (partner.getFavoritePartnerId(0) == student.getId()) {
                    updateScores(student, partner);
                    makePartner(student, partner);
                }
            }
        }
    }

    private void randomMatching(boolean checkSuitable) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if(student.hasPartner()) continue;

            for (int j = i + 1; j < students.size(); j++) {
                Student partner = students.get(j);

                if(partner.hasPartner()) continue;
                if(checkSuitable && !isSuitablePartner(student, partner)) continue;

                updateScores(student, partner);
                makePartner(student, partner);
                break;
            }
        }
    }

    private void othersMatching() {
        for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if(!student.hasPartner()) {
                makePartner(student, null);
                decreaseScore(student);
            }
        }
    }

    private boolean isSuitablePartner(Student student, Student partner) {
        if ((student.hasPartner() || partner.hasPartner())) return false;
        if (!duplicated && student.isExpartner(partner)) return false;
        if (mode == MATCHING_MODE_DIFF && student.isMale() == partner.isMale()) return false;
        else if(mode == MATCHING_MODE_SAME && student.isMale() != partner.isMale()) return false;

        return true;
    }

    private void makePartner(Student student, Student partner) {
        student.addPartner(partner);
        student.setHasPartner(true);

        if(partner != null) {
            partner.addPartner(student);
            partner.setHasPartner(true);
        }

        couples.add(new Couple(student, partner));
    }

    private void updateScores(Student student, Student partner) {
        increaseScore(student, partner);
        increaseScore(partner, student);
    }

    private void increaseScore(Student student, Student partner) {
        if(!student.getFavoritePartnerIds().isEmpty()) student.addScore(student.getFavoritePartnerIndex(partner));
        else {
            int score = 1;

            if(!duplicated && !student.isExpartner(partner)) score++;

            switch(mode) {
                case MATCHING_MODE_DIFF:
                    if(student.isMale() != partner.isMale()) score++;
                    break;

                case MATCHING_MODE_SAME:
                    if(student.isMale() == partner.isMale()) score++;
                    break;

                case MATCHING_MODE_NONE:
                    score++;
                    break;
            }

            student.addScore(score);
        }
    }

    private void decreaseScore(Student student) {
        student.addScore(-1);
    }

    private Student getStudent(int id) {
        for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if(student.getId() == id) return student;
        }

        return null;
    }

    private void updateHappiness() {
        for(int i = 0; i < students.size(); i++) {
            students.get(i).calculateHappiness();
        }
    }

    public List<Couple> getCouples() {
        return couples;
    }
}
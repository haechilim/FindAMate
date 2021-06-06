package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Couple;
import com.example.findamate.domain.History;
import com.example.findamate.domain.Student;
import com.example.findamate.view.CoupleView;
import com.example.findamate.view.StudentView;
import com.example.findamate.thread.TimerThread;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class MatchingActivity extends AppCompatActivity {
    private static Context context;
    private static List<Couple> knownCouples = new ArrayList<>();
    private static LinearLayout student1;
    private static LinearLayout student2;
    private static HorizontalScrollView resultContainer;
    private static LinearLayout container;
    private static TextView happiness;
    private static CoupleView exCoupleView;
    private TextView classInformation;
    private List<Couple> couples = new ArrayList<>();
    private List<Student> students = Classroom.students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode", 1);
        boolean duplicated = intent.getBooleanExtra("duplicated", false);

        context = this;
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.HORIZONTAL);

        happiness = findViewById(R.id.happiness);
        classInformation = findViewById(R.id.classInformation);
        resultContainer = findViewById(R.id.resultContainer);
        student1 = findViewById(R.id.student1);
        student2 = findViewById(R.id.student2);

        init(Classroom.getClassInfo());

        matchingPartner(mode, duplicated);
        updateHistory();

        /*for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            for(int j = 0; j < student.getPartners().size(); j++) {
                Student partner = student.getPartners().get(j);
                Log.d("wtf", student.getName() + "    " + partner.getName() + "    " + student.getScore());
            }
        }*/

      /*  LogMatchingAdapter resultAdapter = new LogMatchingAdapter(this, studentsPairList);
        resultList.setAdapter(resultAdapter);*/

        TimerThread timer = new TimerThread(this, couples);
        timer.start();
    }

    private void init(String classInformation) {
        this.classInformation.setText(classInformation);
    }

    private void updateHistory() {
        Classroom.histories.add(new History(Calendar.getInstance(), couples));
    }

    private boolean matchingPartner(int mode, boolean duplicated) {
        if(students.size() <= 0) return false;

        sort();
        clearPartner();
        bestMatching(mode, duplicated);
        secondBestMatching(mode, duplicated);

        return everyoneHasPartner();
    }

    private void sort() {
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getScore() - o1.getScore();
            }
        });
    }

    private void clearPartner() {
        for (int i = 0; i < students.size(); i++) {
            students.get(i).clearHasPartner();
        }
    }

    private void bestMatching(int mode, boolean duplicated) {
        for (int choice = 0; choice < Student.MAX_FAVORITE_SCORE; choice++) {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                Student partner = student.getFavoritePartner(choice);

                if(!isSuitablePartner(student, partner, mode, duplicated)) continue;

                if (partner.getFavoritePartner(0).getId() == student.getId()) {
                    makePartner(student, partner);
                    updateScore(student, partner);
                }
            }
        }
    }

    private void secondBestMatching(int mode, boolean duplicated) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if(student.isHasPartner()) continue;

            for (int choice = i + 1; choice < students.size(); choice++) {
                Student partner = students.get(choice);

                if(!isSuitablePartner(student, partner, mode, duplicated)) continue;

                makePartner(student, partner);
                updateScore(student, partner);
                break;
            }
        }
    }

    private boolean isSuitablePartner(Student student, Student partner, int mode, boolean duplicated) {
        if (!duplicated && (student.isHasPartner() || partner.isHasPartner())) return false;
        if (!duplicated && student.isExpartner(partner)) return false;
        if (mode == PopupMatchingSettingActivity.MATCHING_MODE_DIFF && student.isMale() == partner.isMale()) return false;
        else if (mode == PopupMatchingSettingActivity.MATCHING_MODE_SAME && student.isMale() != partner.isMale()) return false;

        return true;
    }

    private void makePartner(Student student, Student partner) {
        student.addPartner(partner);
        partner.addPartner(student);

        student.setHasPartner(true);
        partner.setHasPartner(true);

        couples.add(new Couple(student, partner));
    }

    private void makePartner(Student student) {
        student.addPartner(student);

        student.setHasPartner(true);

        couples.add(new Couple(student, student));
    }

    private void updateScore(Student student, Student partner) {
        student.addScore(student.getFavoritePartnerIndex(partner));
        partner.addScore(partner.getFavoritePartnerIndex(student));
    }

    private void updateScore(Student student) {
        student.addScore(-1);
    }

    private boolean everyoneHasPartner() {
        for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if(!student.isHasPartner()) {
                makePartner(student);
                updateScore(student);
                return false;
            }
        }

        return true;
    }

    public void animation(Couple couple) {
        resetViews();

        knownCouples.add(couple);
        updateHappiness();

        student1.addView(new StudentView(context, couple.getStudent1()));
        student2.addView(new StudentView(context, couple.getStudent2()));

        if(knownCouples.size() > 1) {
            container.addView(exCoupleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            resultContainer.addView(container);
        }

        exCoupleView = new CoupleView(context, couple.getStudent1(), couple.getStudent2());
    }

    private void resetViews() {
        student1.removeAllViews();
        student2.removeAllViews();
        resultContainer.removeAllViews();
    }

    private void updateHappiness() {
        int count = knownCouples.size();
        double totalHappiness = 0.0;

        for(int i = 0; i < count; i++) {
            Couple couple = knownCouples.get(i);

            totalHappiness += couple.getStudent1().getHappiness() + couple.getStudent2().getHappiness();
        }

        happiness.setText(Math.round(totalHappiness / (count * 2)) + "%");
    }
}
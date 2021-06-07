package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MatchingActivity extends AppCompatActivity {
    private Timer timer;
    private int index = 0;
    private boolean isSimulation;
    private List<Couple> knownCouples = new ArrayList<>();
    private LinearLayout student1;
    private LinearLayout student2;
    private HorizontalScrollView resultContainer;
    private LinearLayout container;
    private CoupleView exCoupleView;
    private TextView classInformation;
    private List<Student> students;
    private List<History> histories;
    private List<Couple> couples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", -1);
        isSimulation = intent.getBooleanExtra("isSimulation", true);
        int mode = intent.getIntExtra("mode", 1);
        boolean duplicated = intent.getBooleanExtra("duplicated", false);

        if (type == LogActivity.TYPE_SIMULATION) {
            students = Classroom.getClonedStudents();
            histories = Classroom.getClonedHistories();
        }
        else {
            students = isSimulation ? Classroom.ClonedStudents() : Classroom.students;
            histories = isSimulation ? Classroom.ClonedHistories() : Classroom.histories;
        }

        timer = new Timer();
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.HORIZONTAL);

        classInformation = findViewById(R.id.classInformation);
        resultContainer = findViewById(R.id.resultContainer);
        student1 = findViewById(R.id.student1);
        student2 = findViewById(R.id.student2);

        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLog();
            }
        });

        init(Classroom.getClassInfo());
        matchingPartner(mode, duplicated);
        updateHistory();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(index >= couples.size()) toLog();
                        else animation(couples.get(index++));
                    }
                });
            }
        };

        timer.schedule(timerTask, 0, 1500);
    }

    private void init(String classInformation) {
        this.classInformation.setText(classInformation);
        knownCouples = new ArrayList<>();
    }

    private void toLog() {
        timer.cancel();
        endAnimation();
    }

    private void updateHistory() {
        histories.add(new History(Calendar.getInstance(), couples));
    }

    private boolean matchingPartner(int mode, boolean duplicated) {
        if(students.size() <= 0) return false;

        sort();
        clearPartner();
        bestMatching(mode, duplicated);
        secondBestMatching(mode, duplicated, true);
        secondBestMatching(mode, duplicated, false);

        return everyoneHasPartner();
    }

    private void sort() {
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getScore() - o2.getScore();
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
                Student partner = Classroom.findStudentById(student.getFavoritePartnerId(choice), isSimulation);

                if(!isSuitablePartner(student, partner, mode, duplicated)) continue;

                if (partner.getFavoritePartnerId(0) == student.getId()) {
                    makePartner(student, partner);
                    updateScore(student, partner);
                }
            }
        }
    }

    private void secondBestMatching(int mode, boolean duplicated, boolean checkSuitable) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if(student.isHasPartner()) continue;

            for (int choice = i + 1; choice < students.size(); choice++) {
                Student partner = students.get(choice);

                if ((student.isHasPartner() || partner.isHasPartner())) continue;
                if(checkSuitable && !isSuitablePartner(student, partner, mode, duplicated)) continue;

                makePartner(student, partner);
                updateScore(student, partner);
                break;
            }
        }
    }

    private boolean isSuitablePartner(Student student, Student partner, int mode, boolean duplicated) {
        if ((student.isHasPartner() || partner.isHasPartner())) return false;
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

        student1.addView(new StudentView(this, couple.getStudent1()));
        student2.addView(new StudentView(this, couple.getStudent2()));

        if(knownCouples.size() > 1) {
            container.addView(exCoupleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            resultContainer.addView(container);
        }

        exCoupleView = new CoupleView(this, couple.getStudent1(), couple.getStudent2());
    }

    public void endAnimation() {
        Intent intent = new Intent(this, LogActivity.class);
        intent.putExtra("type", isSimulation ? LogActivity.TYPE_SIMULATION : LogActivity.TYPE_RESULT);
        this.startActivity(intent);
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

        ((TextView)findViewById(R.id.happiness)).setText(Math.round(totalHappiness / (count * 2)) + "%");
    }
}
package com.example.findamate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Couple;
import com.example.findamate.domain.History;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Logger;
import com.example.findamate.manager.ApiManager;
import com.example.findamate.manager.MatchingManager;
import com.example.findamate.manager.StudentViewManager;
import com.example.findamate.view.CoupleView;
import com.example.findamate.view.StudentView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MatchingActivity extends AppCompatActivity {
    private Timer timer;
    private int coupleIndex;
    private boolean isSimulation;
    private int mode;
    private boolean duplicated;
    private LinearLayout student1;
    private LinearLayout student2;
    private ImageView versus;
    private LinearLayout happinessContainer;
    private LinearLayout resultContainer;
    private CoupleView coupleView;
    private TextView classInformation;
    private List<Student> students;
    private List<Couple> couples;
    private List<History> histories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", -1);
        isSimulation = intent.getBooleanExtra("isSimulation", true);
        mode = intent.getIntExtra("mode", 1);
        duplicated = intent.getBooleanExtra("duplicated", false);

        if (type == LogActivity.TYPE_SIMULATION) {
            students = Classroom.getClonedStudents();
            histories = Classroom.getClonedHistories();
        }
        else {
            students = isSimulation ? Classroom.clonedStudents() : Classroom.students;
            histories = isSimulation ? Classroom.clonedHistories() : Classroom.histories;
        }

        classInformation = findViewById(R.id.classInformation);
        happinessContainer = findViewById(R.id.happinessContainer);
        resultContainer = findViewById(R.id.resultContainer);
        student1 = findViewById(R.id.student1);
        student2 = findViewById(R.id.student2);
        versus = findViewById(R.id.versus);

        init(Classroom.getClassInfo());
        bindEvents();
        startMatching();
    }

    @Override
    public void onBackPressed() {}

    private void startMatching() {
        if(isSimulation) {
            match();
            startAnimation();
            addHistory();
            return;
        }

        ApiManager.addRound(new ApiManager.AddRoundCallback() {
            @Override
            public void success(History history) {
                match();
                startAnimation();
                updateDb(history.getId());
            }
        });
    }

    private void match() {
        MatchingManager matchingManager = new MatchingManager(isSimulation, mode, duplicated, students);
        matchingManager.match();
        couples = matchingManager.getCouples();
    }

    private void startAnimation() {
        coupleIndex = 0;
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateResultView();
                        if(coupleIndex >= couples.size()) startLogActivity();
                        else {
                            startMatchingAnimation(couples.get(coupleIndex));
                            coupleIndex++;
                        }
                    }
                });
            }
        };

        timer.schedule(timerTask, 0, 5000);
    }

    private void bindEvents() {
        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogActivity();
            }
        });
    }

    private void init(String classInformation) {
        this.classInformation.setText(classInformation);
    }

    private void updateDb(int roundId) {
        for(int i = 0; i < couples.size(); i++) {
            Couple couple = couples.get(i);
            ApiManager.addMate(couple.getStudent1(), couple.getStudent2(), roundId);
        }

        for(int i = 0; i < students.size(); i++) {
            ApiManager.modifyStudent(students.get(i), null);
        }
    }

    private void addHistory() {
        History history = new History();
        history.setDate(new Date());
        history.setCouples(couples);
        histories.add(history);
    }

    private void startMatchingAnimation(Couple couple) {
        resetViews();
        updateHappiness();

        student1.addView(new StudentView(this, couple.getStudent1()));
        student2.addView(new StudentView(this, couple.getStudent2()));
        coupleView = new CoupleView(this, couple.getStudent1(), couple.getStudent2(), false);

        StudentViewManager.startMatchingAnimation(this, student1, student2, versus, happinessContainer);
    }

    private void updateResultView() {
        if(coupleView == null) return;
        resultContainer.addView(coupleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void startLogActivity() {
        timer.cancel();

        Intent intent = new Intent(this, LogActivity.class);
        intent.putExtra("type", isSimulation ? LogActivity.TYPE_SIMULATION : LogActivity.TYPE_RESULT);
        startActivity(intent);
    }

    private void resetViews() {
        student1.removeAllViews();
        student2.removeAllViews();
    }

    private void updateHappiness() {
        int count = 0;
        double total = 0;
        int round = 0;
        String result = "-";

        for(int i = 0; i <= coupleIndex; i++) {
            if(i >= couples.size()) break;

            Couple couple = couples.get(i);
            Student student1 = couple.getStudent1();
            Student student2 = couple.getStudent2();

            if(student1 != null) {
                total += student1.getHappiness();
                count++;
                if(round == 0) round = student1.getPartnerIds().size();
            }

            if(student2 != null) {
                total += student2.getHappiness();
                count++;
            }
        }

        if(count > 0 && round > 0) result = String.format("%d%%", Math.round(total / count));

        ((TextView)findViewById(R.id.happiness)).setText(result);
    }
}
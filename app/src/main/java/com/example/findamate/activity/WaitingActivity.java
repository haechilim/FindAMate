package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Logger;
import com.example.findamate.manager.StudentViewManager;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class WaitingActivity extends AppCompatActivity {
    FrameLayout studentContainer;
    FrameLayout tip;
    List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        init();
        bindEvents();
    }

    private void init() {
        ((TextView)findViewById(R.id.classInfo)).setText(Classroom.getClassInfo());
        studentContainer = findViewById(R.id.studentContainer);
        tip = findViewById(R.id.tip);
        students = Classroom.students;

        drawStudents();

        SharedPreferences sharedPreferences = getSharedPreferences("waitingTip", MODE_PRIVATE);
        showTip(sharedPreferences.getBoolean("showTip", true));
    }

    private void drawStudents() {
        for(int i = 0; i < students.size(); i++) {
            View view = (View)StudentViewManager.newView(this, students.get(i), false).getParent();
            studentContainer.addView(view);
        }

        StudentViewManager.randomPositions(this, studentContainer);
        StudentViewManager.startBounceAnimation(this, studentContainer);
    }

    private void bindEvents() {
        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode", 0);
        boolean duplicated = intent.getBooleanExtra("duplicated", false);

        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitTip();
                showTip(false);
            }
        });

        findViewById(R.id.startMatching).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitTip();

                Intent intent = new Intent(getBaseContext(), MatchingActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isSimulation", false);
                intent.putExtra("mode", mode);
                intent.putExtra("duplicated", duplicated);
                startActivity(intent);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitTip();
                finish();
            }
        });
    }

    private void showTip(boolean visibility) {
        tip.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void commitTip() {
        SharedPreferences sharedPreferences = getSharedPreferences("waitingTip", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("showTip", false);
        editor.commit();
    }
}
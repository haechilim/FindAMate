package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Student;
import com.example.findamate.manager.StudentViewManager;

import java.util.List;

public class WaitingActivity extends AppCompatActivity {
    FrameLayout studentContainer;
    List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        ((TextView)findViewById(R.id.classInfo)).setText(Classroom.getClassInfo());
        studentContainer = findViewById(R.id.studentContainer);
        students = Classroom.students;

        for(int i = 0; i < students.size(); i++) {
            studentContainer.addView(StudentViewManager.newView(this, students.get(i), false));
        }

        StudentViewManager.randomPositions(this, studentContainer);
        StudentViewManager.startBounceAnimation(this, studentContainer);

        bindEvents();
    }

    private void bindEvents() {
        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode", 0);
        boolean duplicated = intent.getBooleanExtra("duplicated", false);

        findViewById(R.id.startMatching).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MatchingActivity.class);
                intent.putExtra("isSimulation", false);
                intent.putExtra("mode", mode);
                intent.putExtra("duplicated", duplicated);
                startActivity(intent);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
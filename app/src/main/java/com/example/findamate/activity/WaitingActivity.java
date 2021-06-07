package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Student;
import com.example.findamate.manager.StudentViewManager;

import java.util.ArrayList;
import java.util.List;

public class WaitingActivity extends AppCompatActivity {
    LinearLayout workspace;
    List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode", 0);
        boolean duplicated = intent.getBooleanExtra("duplicated", false);

        ((TextView)findViewById(R.id.classInfo)).setText(Classroom.getClassInfo());
        workspace = findViewById(R.id.workspace);
        students = Classroom.students;

        for(int i = 0; i < students.size(); i++) {
            workspace.addView(StudentViewManager.getView(this, students.get(i), false));
        }

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
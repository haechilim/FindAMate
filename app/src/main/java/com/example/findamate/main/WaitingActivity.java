package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.StudentView;

import java.util.ArrayList;
import java.util.List;

public class WaitingActivity extends AppCompatActivity {
    TextView classInformationOfWaiting;
    LinearLayout studentContainerOfWaiting;
    Button startMatching;

    List<Student> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        classInformationOfWaiting = findViewById(R.id.classInformationOfWaiting);
        studentContainerOfWaiting = findViewById(R.id.studentContainerOfWaiting);
        startMatching = findViewById(R.id.startMatching);

        studentList.add(new Student("20519", "임준형"));
        studentList.add(new Student("2400", "랄로"));
        studentList.add(new Student("2401", "파카"));
        studentList.add(new Student("9999", "도파"));
        studentList.add(new Student("기모링", "괴물쥐"));

        Intent intent = getIntent();
        String classInformation = intent.getStringExtra("classInformation");
        int matchingModeId = intent.getIntExtra("matchingModeId", -1);
        boolean overlap = intent.getBooleanExtra("overlap", false);

        classInformationOfWaiting.setText(classInformation);

        for(int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);

            StudentView studentProfile = new StudentView(this, student.getId(), student.getName());

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 400);
            studentContainerOfWaiting.addView(studentProfile, layoutParams);
        }

        startMatching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MatchingActivity.class);
                intent.putExtra("classInformation", classInformation);
                intent.putExtra("matchingModeId", matchingModeId);
                intent.putExtra("overlap", overlap);
                startActivity(intent);
            }
        });
    }
}
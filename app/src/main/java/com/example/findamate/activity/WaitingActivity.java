package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.domain.Student;
import com.example.findamate.manager.StudentViewManager;

import java.util.ArrayList;
import java.util.List;

public class WaitingActivity extends AppCompatActivity {
    LinearLayout studentContainerOfWaiting;
    List<Student> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        studentContainerOfWaiting = findViewById(R.id.studentContainerOfWaiting);

        studentList.add(new Student("20519", "임준형",true,"haechilim", 10, "카무이!"));
        studentList.add(new Student("2400", "랄로",true,"haechilim", 22, "카무이!"));
        studentList.add(new Student("2401", "파카",true,"haechilim", 49, "카무이!"));
        studentList.add(new Student("9999", "도파",true,"haechilim", 27, "카무이!"));
        studentList.add(new Student("기모링", "괴물쥐",true,"haechilim", 44, "카무이!"));

        Intent intent = getIntent();
        String classInformation = intent.getStringExtra("classInformation");
        int matchingModeId = intent.getIntExtra("matchingModeId", 0);
        boolean overlap = intent.getBooleanExtra("overlap", false);

        ((TextView)findViewById(R.id.classInformationOfWaiting)).setText(classInformation);

        for(int i = 0; i < studentList.size(); i++) {
            studentContainerOfWaiting.addView(StudentViewManager.getView(this, studentList.get(i), false));
        }

        findViewById(R.id.startMatching).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MatchingActivity.class);
                intent.putExtra("classInformation", classInformation);
                intent.putExtra("isSimulation", false);
                intent.putExtra("matchingModeId", matchingModeId);
                intent.putExtra("overlap", overlap);
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
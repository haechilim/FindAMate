package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.adapter.LogAdapter;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.Couple;
import com.example.findamate.domain.History;
import com.example.findamate.domain.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LogActivity extends AppCompatActivity {
    private TextView classInformation;
    private ListView logList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        classInformation = findViewById(R.id.classInformationOfLog);
        logList = findViewById(R.id.logList);

        Intent data = getIntent();
        classInformation.setText(data.getStringExtra("classInformation"));

        List<Couple> couples = new ArrayList<>();
        List<History> histories = new ArrayList<>();

        couples.add(new Couple(Classroom.students.get(0), Classroom.students.get(1)));
        couples.add(new Couple(Classroom.students.get(2), Classroom.students.get(3)));
        couples.add(new Couple(Classroom.students.get(4), Classroom.students.get(5)));
        couples.add(new Couple(Classroom.students.get(6), Classroom.students.get(7)));
        couples.add(new Couple(Classroom.students.get(8), Classroom.students.get(9)));
        couples.add(new Couple(Classroom.students.get(10), Classroom.students.get(11)));
        couples.add(new Couple(Classroom.students.get(12), Classroom.students.get(12)));

        histories.add(new History(Calendar.getInstance(), couples));
        histories.add(new History(Calendar.getInstance(), couples));

        LogAdapter logAdapter = new LogAdapter(this, histories);
        logList.setAdapter(logAdapter);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
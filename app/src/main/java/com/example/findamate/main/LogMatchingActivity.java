package com.example.findamate.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.findamate.R;
import com.example.findamate.adapter.LogMatchingAdapter;
import com.example.findamate.domain.History;
import com.example.findamate.domain.MatchingManager;
import com.example.findamate.domain.Student;
import com.example.findamate.domain.StudentsPair;

public class LogMatchingActivity extends AppCompatActivity {
    History history = new History();
    ListView studentsPairList;
    Button closeHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_matching);

        history.addStudentsPair(new StudentsPair(new Student("20519", "임준형"), new Student("2400", "랄로")));
        history.addStudentsPair(new StudentsPair(new Student("20514", "파카"), new Student("2401", "진용진")));
        history.addStudentsPair(new StudentsPair(new Student("20516", "괴물쥐"), new Student("2402", "전국진")));
        history.addStudentsPair(new StudentsPair(new Student("20518", "로지컬"), new Student("2403", "논리왕 전기")));
        history.addStudentsPair(new StudentsPair(new Student("20510", "도파"), new Student("2404", "미야")));
        history.addStudentsPair(new StudentsPair(new Student("24519", "감스트"), new Student("2405", "구루루")));

        studentsPairList = findViewById(R.id.studentsPairList);
        closeHistory = findViewById(R.id.closeHistory);

        LogMatchingAdapter logMatchingAdapter = new LogMatchingAdapter(this, history.getStudentsPairList());
        studentsPairList.setAdapter(logMatchingAdapter);

        closeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
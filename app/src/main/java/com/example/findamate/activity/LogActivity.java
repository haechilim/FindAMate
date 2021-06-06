package com.example.findamate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findamate.R;
import com.example.findamate.adapter.LogAdapter;
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

        List<Student> students = new ArrayList<>();
        List<Couple> couples = new ArrayList<>();
        List<History> histories = new ArrayList<>();

        students.add(new Student("20519", "임준형",true,"haechilim", R.drawable.avatar10, "카무이!"));
        students.add(new Student("2400", "랄로",true,"haechilim", R.drawable.avatar22, "카무이!"));
        students.add(new Student("20514", "파카",true,"haechilim", R.drawable.avatar49, "카무이!"));
        students.add(new Student("20516", "괴물쥐",false,"haechilim", R.drawable.avatar27, "카무이!"));
        students.add(new Student("20518", "로지컬",true,"haechilim", R.drawable.avatar44, "카무이!"));
        students.add(new Student("20510", "도파",false,"haechilim", R.drawable.avatar30, "카무이!"));
        students.add(new Student("24519", "감스트",true,"haechilim", R.drawable.avatar29, "카무이!"));
        students.add(new Student("2401", "진용진",true,"haechilim", R.drawable.avatar19, "카무이!"));
        students.add(new Student("2402", "전국진",false,"haechilim", R.drawable.avatar11, "카무이!"));
        students.add(new Student("2403", "논리왕 전기",false,"haechilim", R.drawable.avatar33, "카무이!"));
        students.add(new Student("2404", "미야",false,"haechilim", R.drawable.avatar32, "카무이!"));
        students.add(new Student("2405", "구루루",true,"haechilim", R.drawable.avatar23, "카무이!"));
        students.add(new Student("1105", "우주하마",true,"haechilim", R.drawable.avatar43, "카무이!"));

        couples.add(new Couple(students.get(0), students.get(1)));
        couples.add(new Couple(students.get(2), students.get(3)));
        couples.add(new Couple(students.get(4), students.get(5)));
        couples.add(new Couple(students.get(6), students.get(7)));
        couples.add(new Couple(students.get(8), students.get(9)));
        couples.add(new Couple(students.get(10), students.get(11)));
        couples.add(new Couple(students.get(12), students.get(12)));

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
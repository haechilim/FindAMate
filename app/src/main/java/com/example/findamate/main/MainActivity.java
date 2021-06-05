package com.example.findamate.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.School;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.StudentView;

// 배경색: #323232
// 전체만족도 : #c7c7c7
// 전체만족도(값) : #42d2c4
public class MainActivity extends AppCompatActivity {
    private LinearLayout studentContainer;
    private TextView schoolView;
    private School school = Classroom.school;
    private String schoolInformation;
    private Student targetStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentContainer = findViewById(R.id.studentContainer);
        schoolView = findViewById(R.id.classInformation);

        load();
        updateUi();
        bindEvents();
    }

    private void load() {
        loadSchool();
        loadStudents();
    }

    private void loadSchool() {
        Classroom.school.setName("선린고");
        Classroom.school.setYear("2");
        Classroom.school.setNumber("5");
    }

    private void loadStudents() {
        //임시 데이터
        Classroom.students.add(new Student("20519", "임준형", true, "haechilim", R.drawable.avatar04, "카무이!"));
        Classroom.students.add(new Student("2400", "랄로", false, "haechilim", R.drawable.avatar51, "카무이!"));
        Classroom.students.add(new Student("2401", "파카", true, "haechilim", R.drawable.avatar30, "카무이!"));
        Classroom.students.add(new Student("9999", "도파", false, "haechilim", R.drawable.avatar22, "카무이!"));
        Classroom.students.add(new Student("2222", "괴물쥐", false, "haechilim", R.drawable.avatar27, "카무이!"));
    }

    private void updateUi() {
        updateSchool();
        removeStudentView();
        addStudentViews();
    }

    private void updateSchool() {
        schoolInformation = String.format("%s %s학년 %s반", school.getName(), school.getYear(), school.getNumber());
        schoolView.setText(schoolInformation);
    }

    private void addStudentViews() {
        for(int i = 0; i < Classroom.students.size(); i++) {
            addStudentView(Classroom.students.get(i));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == 100) {
            school.setName(data.getStringExtra("name"));
            school.setYear(data.getStringExtra("year"));
            school.setNumber(data.getStringExtra("number"));
            updateSchool();
        }
        else if(requestCode == 1 && resultCode == 200) {
            data.getStringExtra("name");
            data.getStringExtra("male");
            data.getStringExtra("talkId");
            updateSchool();
        }
        else if(requestCode == 2) {
            Intent intent;

            if( resultCode == 300) {
                intent = new Intent(this, WaitingActivity.class);
                intent.putExtra("classInformation", school.getName() + " " + school.getYear() + "학년 " + school.getNumber() + "반");
                intent.putExtra("isSimulation", false);
                intent.putExtra("matchingModeId", data.getIntExtra("matchingModeId", 0));
                intent.putExtra("overlap", data.getBooleanExtra("overlap", false));
                startActivity(intent);
            }
            else if(resultCode == 400) {
                String a = data.getStringExtra("male");
                targetStudent.setName(data.getStringExtra("name"));
                targetStudent.setMale(data.getStringExtra("male").equals("남"));
                targetStudent.setSnsId(data.getStringExtra("talkId"));
                updateUi();
            }
        }
        else if(requestCode == 3 && resultCode == 300) {
            Intent intent = new Intent(this, MatchingActivity.class);
            intent.putExtra("classInformation", school.getName() + " " + school.getYear() + "학년 " + school.getNumber() + "반");
            intent.putExtra("isSimulation", true);
            intent.putExtra("matchingModeId", data.getIntExtra("matchingModeId", 0));
            intent.putExtra("overlap", data.getBooleanExtra("overlap", false));
            startActivity(intent);
        }
    }

    private void bindEvents() {
        findViewById(R.id.classSettingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupClassSettingActivity.class);
                intent.putExtra("name", school.getName());
                intent.putExtra("year", school.getYear());
                intent.putExtra("number", school.getNumber());
                startActivityForResult(intent, 0);
            }
        });

        findViewById(R.id.studentSettingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupStudentSettingActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        findViewById(R.id.logButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                intent.putExtra("classInformation", schoolInformation);
                startActivity(intent);
            }
        });

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupMatchingSettingActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        findViewById(R.id.simulationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupMatchingSettingActivity.class);
                startActivityForResult(intent, 3);
            }
        });
    }

    private void addStudentView(Student student) {
        StudentView studentView = new StudentView(this, student);

        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 400);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 250);

        studentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                targetStudent = student;

                Intent intent = new Intent(MainActivity.this, PopupStudentSettingActivity.class);
                intent.putExtra("name", student.getName());
                intent.putExtra("male", student.isMale());
                intent.putExtra("talkId", student.getSnsId());
                startActivityForResult(intent, 2);

                return true;
            }
        });

        studentContainer.addView(studentView, layoutParams);
    }

    private void removeStudentView() {
        studentContainer.removeAllViews();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
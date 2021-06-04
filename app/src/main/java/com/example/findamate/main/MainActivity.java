package com.example.findamate.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    /*private ImageView classSettingCancelButton;
    private LinearLayout classSettingLayout;
    private EditText schoolName;
    private EditText grade;
    private EditText classNumber;
    private Button applyButton;
    private EditText studentId;
    private EditText studentName;
    private ImageView addStudentButton;
    private ImageView studentSettingCancelButton;
    private LinearLayout studentSettingLayout;*/
    private School school = Classroom.school;
    private String schoolInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentContainer = findViewById(R.id.studentContainer);
        schoolView = findViewById(R.id.classInformation);
        /*simulationButton = findViewById(R.id.simulationButton);
        startButton = findViewById(R.id.startButton);
        classSettingCancelButton = findViewById(R.id.classSettingCancelButton);
        classSettingLayout = findViewById(R.id.classSettingLayout);
        schoolName = findViewById(R.id.schoolName);
        grade = findViewById(R.id.grade);
        classNumber = findViewById(R.id.classNumber);
        applyButton = findViewById(R.id.applyButton);
        studentId = findViewById(R.id.studentId);
        studentName = findViewById(R.id.studentName);
        addStudentButton = findViewById(R.id.addStudentButton);
        studentSettingCancelButton = findViewById(R.id.studentSettingCancelButton);
        studentSettingLayout = findViewById(R.id.studentSettingLayout);*/

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
        Classroom.students.add(new Student("20519", "임준형"));
        Classroom.students.add(new Student("2400", "랄로"));
        Classroom.students.add(new Student("2401", "파카"));
        Classroom.students.add(new Student("9999", "도파"));
        Classroom.students.add(new Student("기모링", "괴물쥐"));
    }

    private void updateUi() {
        updateSchool();
        addStudentViews();
    }

    private void updateSchool() {
        schoolInformation = String.format("%s %s학년 %s반", school.getName(), school.getYear(), school.getNumber());
        schoolView.setText(schoolInformation);
    }

    private void addStudentViews() {
        for(int i = 0; i < Classroom.students.size(); i++) {
            Student student = Classroom.students.get(i);

            addStudentView(student.getId(), student.getName());
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
        else if(requestCode == 2 && resultCode == 300) {
            Intent intent = new Intent(this, WaitingActivity.class);
            intent.putExtra("name", school.getName());
            intent.putExtra("year", school.getYear());
            intent.putExtra("number", school.getNumber());
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

        /*classSettingCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClassSettingButton(true);
                showClassSettingCancelButton(false);
                showClassSettingLayout(true);
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String schoolName = MainActivity.this.schoolName.getText().toString().trim();
                String year = MainActivity.this.grade.getText().toString().trim();
                String number = classNumber.getText().toString().trim();

                if(!schoolName.isEmpty() && !year.isEmpty() && !number.isEmpty()) {
                    School school = Classroom.school;
                    school.setName(schoolName);
                    school.setYear(year);
                    school.setYear(number);

                    updateSchool();
                    hideKeyboard();
                }
            }
        });

        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = studentId.getText().toString().trim();
                String name = studentName.getText().toString().trim();

                if(!id.isEmpty() && !name.isEmpty()) {
                    //DataBaseStudentList dataBaseStudentList = new DataBaseStudentList(activity);
                    addStudentView(id, name);
                    studentId.setText("");
                    studentName.setText("");
                    hideKeyboard();
                }
            }
        });

        studentSettingCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudentSettingButton(true);
                showStudentSettingCancelButton(false);
                showStudentSettingLayout(true);
            }
        });

        simulationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                intent.putExtra("isSimulation", true);
                intent.putExtra("classInformation", schoolView.getText().toString());
                startActivity(intent);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                intent.putExtra("isSimulation", false);
                intent.putExtra("classInformation", schoolView.getText().toString());
                startActivity(intent);
            }
        });*/
    }

    private void addStudentView(String id, String name) {
        StudentView studentView = new StudentView(this, id, name);

        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 400);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 250);
        studentContainer.addView(studentView, layoutParams);
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*private void showClassSettingButton(boolean visibility) {
        classSettingButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showClassSettingCancelButton(boolean visibility) {
        classSettingCancelButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showClassSettingLayout(boolean visibility) {
        classSettingLayout.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showStudentSettingButton(boolean visibility) {
        studentSettingButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showStudentSettingCancelButton(boolean visibility) {
        studentSettingCancelButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void showStudentSettingLayout(boolean visibility) {
        studentSettingLayout.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }*/
}
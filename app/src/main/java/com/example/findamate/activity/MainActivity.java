package com.example.findamate.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findamate.R;
import com.example.findamate.domain.Classroom;
import com.example.findamate.domain.School;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Logger;
import com.example.findamate.manager.ApiManager;
import com.example.findamate.manager.StudentViewManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int POPUP_CLASS = 0;
    private static final int POPUP_STUDENT = 1;
    private static final int POPUP_MATCHING = 2;
    private static final int POPUP_SIMULATION = 3;

    private FrameLayout studentContainer;
    private TextView schoolView;
    private Student targetStudent;

    private List<Rect> studentViewPositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentContainer = findViewById(R.id.studentContainer);
        schoolView = findViewById(R.id.classInformation);

        load();
        updateUi();
        bindEvents();

        studentViewPositions = StudentViewManager.randomPositions(this, studentContainer);
        StudentViewManager.startWaveAnimation(this, studentContainer);

        ApiManager.setMemberId(0);
        ApiManager.school(new ApiManager.SchoolCallback() {
            @Override
            public void success(School school) {
                Classroom.school = school;
                updateSchool();
            }
        });
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
        List<Student> students = Classroom.students;

        if(!students.isEmpty()) return;

        students.add(new Student("임준형",true,"haechilim", 10, "카무이!"));
        students.add(new Student("랄로",true,"haechilim", 22, "카무이!"));
        students.add(new Student("파카",true,"haechilim", 49, "카무이!"));
        students.add(new Student("괴물쥐",false,"haechilim", 27, "카무이!"));
        students.add(new Student("로지컬",true,"haechilim", 44, "카무이!"));
        students.add(new Student("도파",false,"haechilim", 30, "카무이!"));
        students.add(new Student("감스트",true,"haechilim", 29, "카무이!"));
        students.add(new Student("진용진",true,"haechilim", 19, "카무이!"));
        students.add(new Student("전국진",false,"haechilim", 11, "카무이!"));
        students.add(new Student("논리왕 전기",false,"haechilim", 33, "카무이!"));
        students.add(new Student("미야",false,"haechilim", 32, "카무이!"));
        students.add(new Student("구루루",true,"haechilim", 23, "카무이!"));
        students.add(new Student("우주하마",true,"haechilim", 43, "카무이!"));

        students.get(0).addFavoritePartner(students.get(1));
        students.get(0).addFavoritePartner(students.get(8));
        students.get(0).addFavoritePartner(students.get(3));

        students.get(1).addFavoritePartner(students.get(10));
        students.get(1).addFavoritePartner(students.get(11));
        students.get(1).addFavoritePartner(students.get(9));

        students.get(2).addFavoritePartner(students.get(9));
        students.get(2).addFavoritePartner(students.get(6));
        students.get(2).addFavoritePartner(students.get(10));

        students.get(3).addFavoritePartner(students.get(1));
        students.get(3).addFavoritePartner(students.get(0));
        students.get(3).addFavoritePartner(students.get(10));

        students.get(4).addFavoritePartner(students.get(7));
        students.get(4).addFavoritePartner(students.get(8));
        students.get(4).addFavoritePartner(students.get(9));

        students.get(5).addFavoritePartner(students.get(1));
        students.get(5).addFavoritePartner(students.get(2));
        students.get(5).addFavoritePartner(students.get(0));

        students.get(6).addFavoritePartner(students.get(4));
        students.get(6).addFavoritePartner(students.get(0));
        students.get(6).addFavoritePartner(students.get(5));

        students.get(7).addFavoritePartner(students.get(8));
        students.get(7).addFavoritePartner(students.get(6));
        students.get(7).addFavoritePartner(students.get(10));

        students.get(8).addFavoritePartner(students.get(3));
        students.get(8).addFavoritePartner(students.get(11));
        students.get(8).addFavoritePartner(students.get(5));

        students.get(9).addFavoritePartner(students.get(4));
        students.get(9).addFavoritePartner(students.get(10));
        students.get(9).addFavoritePartner(students.get(6));

        students.get(10).addFavoritePartner(students.get(1));
        students.get(10).addFavoritePartner(students.get(5));
        students.get(10).addFavoritePartner(students.get(4));

        students.get(11).addFavoritePartner(students.get(9));
        students.get(11).addFavoritePartner(students.get(4));
        students.get(11).addFavoritePartner(students.get(7));

        students.get(12).addFavoritePartner(students.get(9));
        students.get(12).addFavoritePartner(students.get(4));
        students.get(12).addFavoritePartner(students.get(7));
    }

    private void updateUi() {
        updateSchool();
        removeStudentView();
        addStudentViews();
    }

    private void updateSchool() {
        schoolView.setText(Classroom.getClassInfo());
    }

    private void addStudentViews() {
        for(int i = 0; i < Classroom.students.size(); i++) {
            addStudentView(Classroom.students.get(i));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case POPUP_CLASS:
                onResultSchool(resultCode);
                break;

            case POPUP_STUDENT:
                onResultStudent(resultCode, data);
                break;

            case POPUP_MATCHING:
                onResultRun(resultCode, data, false);
                break;

            case POPUP_SIMULATION:
                onResultRun(resultCode, data, true);
                break;
        }
    }

    private void onResultSchool(int resultCode) {
        if(resultCode == RESULT_OK) updateSchool();
    }

    private void onResultStudent(int resultCode, Intent data) {
        if(resultCode == PopupStudentSettingActivity.RESULT_ADD) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            boolean male = data.getBooleanExtra("male", true);

            Classroom.students.add(new Student(name, male, phone, (int)(Math.random() * 54 + 1), ""));
            updateUi();
        }
        else if(resultCode == PopupStudentSettingActivity.RESULT_MODIFY) {
            targetStudent.setName(data.getStringExtra("name"));
            targetStudent.setMale(data.getBooleanExtra("male", true));
            targetStudent.setPhone(data.getStringExtra("phone"));
            updateUi();
        }
        else if(resultCode == PopupStudentSettingActivity.RESULT_REMOVE) {
            for(int i = 0; i < Classroom.students.size(); i++) {
                if(Classroom.students.get(i) == targetStudent) {
                    Classroom.students.remove(i);
                    updateUi();
                    break;
                }
            }
        }
    }

    private void onResultRun(int resultCode, Intent data, boolean isSimulation) {
        if(resultCode != RESULT_OK) return;

        Intent intent = new Intent(this, isSimulation ? MatchingActivity.class : WaitingActivity.class);
        intent.putExtra("isSimulation", isSimulation);
        intent.putExtra("mode", data.getIntExtra("mode", 0));
        intent.putExtra("duplicate", data.getBooleanExtra("duplicate", false));
        startActivity(intent);
    }

    private void bindEvents() {
        findViewById(R.id.classSettingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupClassSettingActivity.class);
                startActivityForResult(intent, POPUP_CLASS);
            }
        });

        findViewById(R.id.studentSettingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupStudentSettingActivity.class);
                startActivityForResult(intent, POPUP_STUDENT);
            }
        });

        findViewById(R.id.logButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                intent.putExtra("type", LogActivity.TYPE_HISTORY);
                startActivity(intent);
            }
        });

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupMatchingSettingActivity.class);
                startActivityForResult(intent, POPUP_MATCHING);
            }
        });

        findViewById(R.id.simulationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupMatchingSettingActivity.class);
                startActivityForResult(intent, POPUP_SIMULATION);
            }
        });
    }

    private void addStudentView(Student student) {
        View studentView = StudentViewManager.getView(this, student, false);

        studentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                targetStudent = student;

                Intent intent = new Intent(MainActivity.this, PopupStudentSettingActivity.class);
                intent.putExtra("id", student.getId());
                startActivityForResult(intent, POPUP_STUDENT);

                return true;
            }
        });

        studentContainer.addView(studentView);
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